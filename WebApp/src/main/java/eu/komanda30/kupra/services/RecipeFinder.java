package eu.komanda30.kupra.services;

import eu.komanda30.kupra.entity.Recipe;

import java.util.List;

import javax.transaction.Transactional;

public interface RecipeFinder {
    @Transactional
    void indexRecipes();
    @Transactional
    List<Recipe> searchForRecipes(String searchText);
}
