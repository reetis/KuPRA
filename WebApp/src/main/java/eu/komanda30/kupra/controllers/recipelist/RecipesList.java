package eu.komanda30.kupra.controllers.recipelist;

import java.util.LinkedList;

/**
 * Created by Rytis on 2014-11-27
 */
public class RecipesList {
    private LinkedList<RecipePreview> recipes = new LinkedList<>();

    public void addRecipe(RecipePreview recipe) {
        recipes.add(recipe);
    }

    public LinkedList<RecipePreview> getRecipes() {
        return recipes;
    }
}
