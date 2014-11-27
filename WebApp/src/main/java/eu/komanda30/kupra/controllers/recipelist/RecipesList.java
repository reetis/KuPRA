package eu.komanda30.kupra.controllers.recipelist;

import java.util.LinkedList;

/**
 * Created by Ignas on 10/23/2014.
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
