package eu.komanda30.kupra.controllers.recipelist;

import java.util.LinkedList;

public class RecipesList {
    private LinkedList<RecipePreview> recipes = new LinkedList<>();

    public void addRecipe(RecipePreview recipe) {
        recipes.add(recipe);
    }

    public LinkedList<RecipePreview> getRecipes() {
        return recipes;
    }
}
