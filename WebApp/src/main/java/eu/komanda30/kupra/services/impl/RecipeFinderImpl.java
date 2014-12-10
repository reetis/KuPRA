package eu.komanda30.kupra.services.impl;

import eu.komanda30.kupra.entity.Friendship;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.repositories.Friendships;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.services.RecipeFinder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.avro.reflect.Nullable;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeFinderImpl implements RecipeFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeFinderImpl.class);

    @Resource
    private EntityManager entityManager;

    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private Friendships friendships;

    @PostConstruct
    public void reindexEntities() {
        indexRecipes();
    }

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

    @Transactional
    @Override
    public List<Recipe> searchForRecipes(String searchText) {
        final FullTextEntityManager fullTextEntityManager = Search
                .getFullTextEntityManager(entityManager);

        final QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Recipe.class).get();

        final Query query = qb.bool()
                .should(sharedRecipesByText(searchText, qb))
                .should(publicRecipesByText(searchText, qb)).createQuery();

        final FullTextQuery textQuery = fullTextEntityManager
                .createFullTextQuery(query, Recipe.class);

        return textQuery.getResultList();
    }

    private Query sharedRecipesByText(String searchText, QueryBuilder qb) {
        return qb.bool()
                .must(sharedRecipesQuery(qb))
                .must(allFieldsQuery(searchText, qb))
                .createQuery();
    }

    private Query publicRecipesByText(String searchText, QueryBuilder qb) {
        return qb.bool()
                .must(publicRecipesQuery(qb))
                .must(publicFieldsQuery(searchText, qb))
                .createQuery();
    }

    private Query sharedRecipesQuery(QueryBuilder qb) {
        final Query friendQuery = friendRecipesQuery(qb);
        final Query ownQuery = ownedRecipesQuery(qb);

        if (friendQuery == null) {
            return ownQuery;
        } else {
            return qb.bool()
                    .should(ownQuery)
                    .should(friendQuery)
                    .createQuery();
        }
    }

    private Query allFieldsQuery(String searchText, QueryBuilder qb) {
        return qb.keyword().fuzzy().withPrefixLength(3)
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

    private Query publicRecipesQuery(QueryBuilder qb) {
        return qb.keyword().onField("publicAccess")
                .matching(Boolean.TRUE)
                .createQuery();
    }

    private Query publicFieldsQuery(String searchText, QueryBuilder qb) {
        return qb.keyword().fuzzy().withPrefixLength(3)
                .onFields(
                        "name",
                        "description",
                        "processDescription",
                        "productNames",
                        "author.username")
                .matching(searchText.trim())
                .createQuery();
    }

    @Nullable
    private Query friendRecipesQuery(QueryBuilder qb) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser user = kupraUsers.findByUsername(auth.getName());

        final Set<Query> queries = friendships.findFriendsOf(user.getUserId())
                .parallelStream()
                .map(Friendship::getTarget)
                .map(friend -> qb.keyword()
                        .onField("author.username")
                        .matching(friend.getUserId())
                        .createQuery())
                .collect(Collectors.toSet());
        if (queries.isEmpty()) {
            return null;
        } else {
            final BooleanJunction<BooleanJunction> junction = qb.bool();
            queries.stream().forEach(junction::should);
            return junction.createQuery();
        }
    }

    private Query ownedRecipesQuery(QueryBuilder qb) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return qb.keyword().onField("author.username").matching(auth.getName())
                .createQuery();
    }
}
