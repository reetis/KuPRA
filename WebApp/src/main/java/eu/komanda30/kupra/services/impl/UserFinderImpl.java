package eu.komanda30.kupra.services.impl;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.services.UserFinder;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserFinderImpl implements UserFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFinderImpl.class);

    @Autowired
    private EntityManager entityManager;

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

    @Transactional
    @Override
    public List<KupraUser> searchForUsers(String searchText) {
        final FullTextEntityManager fullTextEntityManager = Search
                .getFullTextEntityManager(entityManager);

        final QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(KupraUser.class).get();

        final Query query = qb.keyword().fuzzy().withPrefixLength(3)
                .onFields(
                        "username",
                        "profile.fullName",
                        "profile.email",
                        "profile.description")
                .matching(searchText.trim())
                .createQuery();

        final FullTextQuery textQuery = fullTextEntityManager
                .createFullTextQuery(query, KupraUser.class);

        return textQuery.getResultList();
    }
}
