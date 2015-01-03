package eu.komanda30.kupra.services.impl;

import eu.komanda30.kupra.entity.Friendship;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.repositories.Friendships;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.services.RecipeFinder;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RecipeFinderImpl implements RecipeFinder {
    public static final float THRESHOLD = 0.3f;
    public static final int PREFIX_LENGTH = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeFinderImpl.class);
    @Resource
    private EntityManager entityManager;

    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private Friendships friendships;

    @Transactional
    @Override
    public void indexRecipes() {
        final FullTextEntityManager fullTextEntityManager = Search
                .getFullTextEntityManager(entityManager);

        try {
            fullTextEntityManager.createIndexer(Recipe.class).startAndWait();
        } catch (InterruptedException e) {
            LOGGER.error("Failed to index class KupraUser", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<Recipe> searchForRecipes(String searchText, int maxResults) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser user = kupraUsers.findByUsername(auth.getName());
        final List<Friendship> friends = friendships.findFriendsOf(user.getUserId());

        final FullTextEntityManager fullTextEntityManager = Search
                .getFullTextEntityManager(entityManager);

        final QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Recipe.class).get();

        final Query query;
        if (friends.isEmpty()) {
            query = publicRecipesByText(searchText, qb);
        } else {
            query = qb.bool()
                    .should(sharedRecipesByText(friends, searchText, qb))
                    .should(publicRecipesByText(searchText, qb))
                    .createQuery();
        }

        final javax.persistence.Query jpaQuery = fullTextEntityManager
                .createFullTextQuery(query, Recipe.class)
                .setMaxResults(maxResults);

        return (List<Recipe>) jpaQuery.getResultList();
    }

    private Query publicRecipesByText(String searchText, QueryBuilder qb) {
        return qb.bool()
                .must(publicRecipesQuery(qb))
                .must(publicFieldsQuery(searchText, qb))
                .createQuery();
    }

    private Query sharedRecipesByText(List<Friendship> friends, String searchText,
                                      QueryBuilder qb) {
        return qb.bool()
                .must(sharedRecipesQuery(friends, qb))
                .must(allFieldsQuery(searchText, qb))
                .createQuery();
    }

    private Query publicRecipesQuery(QueryBuilder qb) {
        return qb.keyword().onField("publicAccess")
                .matching(Boolean.TRUE)
                .createQuery();
    }

    private Query publicFieldsQuery(String searchText, QueryBuilder qb) {
        return qb.keyword().fuzzy().withThreshold(THRESHOLD).withPrefixLength(PREFIX_LENGTH)
                .onFields(
                        "name",
                        "description",
                        "processDescription",
                        "productNames",
                        "author.username")
                .matching(searchText.trim())
                .createQuery();
    }

    private Query sharedRecipesQuery(List<Friendship> friends, QueryBuilder qb) {
        return qb.bool()
                .should(ownedRecipesQuery(qb))
                .should(friendRecipesQuery(friends, qb))
                .createQuery();
    }

    private Query allFieldsQuery(String searchText, QueryBuilder qb) {
        return qb.keyword().fuzzy().withThreshold(THRESHOLD).withPrefixLength(PREFIX_LENGTH)
                .onFields(
                        "name",
                        "description",
                        "processDescription",
                        "productNames",
                        "author.username",
                        "author.profile.fullName")
                .matching(searchText.trim())
                .createQuery();
    }

    private Query ownedRecipesQuery(QueryBuilder qb) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return qb.keyword().onField("author.username").matching(auth.getName())
                .createQuery();
    }

    private Query friendRecipesQuery(List<Friendship> friends, QueryBuilder qb) {
        final BooleanJunction<BooleanJunction> junction = qb.bool();

        friends.parallelStream()
                .map(Friendship::getTarget)
                .map(friend -> qb.keyword()
                        .onField("author.username")
                        .matching(friend.getUserId())
                        .createQuery())
                .forEach(junction::should);

        return junction.createQuery();
    }
}
