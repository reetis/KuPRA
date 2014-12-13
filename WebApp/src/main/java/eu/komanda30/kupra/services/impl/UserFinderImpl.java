package eu.komanda30.kupra.services.impl;

import eu.komanda30.kupra.entity.Friendship;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.repositories.Friendships;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.services.UserFinder;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserFinderImpl implements UserFinder {
    public static final float THRESHOLD = 0.1f;
    public static final int PREFIX_LENGTH = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFinderImpl.class);
    @Autowired
    private EntityManager entityManager;

    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private Friendships friendships;

    @Transactional
    @Override
    public void indexUsers() {
        final FullTextEntityManager fullTextEntityManager = Search
                .getFullTextEntityManager(entityManager);

        try {
            fullTextEntityManager.createIndexer(KupraUser.class).startAndWait();
        } catch (InterruptedException e) {
            LOGGER.error("Failed to index class KupraUser", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<KupraUser> searchForUsers(String searchText, int maxResults) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser user = kupraUsers.findByUsername(auth.getName());
        final List<Friendship> friends = friendships.findFriendsOf(user.getUserId());

        final FullTextEntityManager fullTextEntityManager = Search
                .getFullTextEntityManager(entityManager);

        final QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(KupraUser.class).get();

        final Query query;
        if (friends.isEmpty()) {
            query = publicProfilesByText(searchText, qb);
        } else {
            query = qb.bool()
                    .should(publicProfilesByText(searchText, qb))
                    .should(friendProfilesByText(friends, searchText, qb))
                    .createQuery();
        }

        final javax.persistence.Query jpaQuery = fullTextEntityManager
                .createFullTextQuery(query, KupraUser.class)
                .setMaxResults(maxResults);

        return (List<KupraUser>) jpaQuery.getResultList();
    }

    private Query publicProfilesByText(String searchText, QueryBuilder qb) {
        return qb.keyword().fuzzy().withThreshold(THRESHOLD).withPrefixLength(PREFIX_LENGTH)
                .onFields("username")
                .matching(searchText.trim())
                .createQuery();
    }

    private Query friendProfilesByText(List<Friendship> friends, String searchText,
                                       QueryBuilder qb) {
        return qb.bool()
                .must(friendsQuery(friends, qb))
                .must(allFieldsQuery(searchText, qb))
                .createQuery();
    }

    private Query friendsQuery(List<Friendship> friends, QueryBuilder qb) {
        final BooleanJunction<BooleanJunction> junction = qb.bool();

        friends.parallelStream()
                .map(Friendship::getTarget)
                .map(friend -> qb.keyword()
                        .onField("username")
                        .matching(friend.getUserId())
                        .createQuery())
                .forEach(junction::should);

        return junction.createQuery();
    }

    private Query allFieldsQuery(String searchText, QueryBuilder qb) {
        return qb.keyword().fuzzy().withThreshold(THRESHOLD).withPrefixLength(PREFIX_LENGTH)
                .onFields(
                        "username",
                        "profile.email",
                        "profile.description",
                        "profile.fullName")
                .matching(searchText.trim())
                .createQuery();
    }

}
