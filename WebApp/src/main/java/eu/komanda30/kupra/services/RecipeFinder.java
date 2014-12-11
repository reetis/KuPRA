package eu.komanda30.kupra.services;

import eu.komanda30.kupra.entity.Recipe;

import java.util.List;

public interface RecipeFinder {
    void indexRecipes();
    List<Recipe> searchForRecipes(String searchText, int maxResults);
}
