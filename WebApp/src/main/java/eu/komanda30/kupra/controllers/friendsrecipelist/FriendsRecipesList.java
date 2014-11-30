package eu.komanda30.kupra.controllers.friendsrecipelist;



import java.util.LinkedList;

/**
 * Created by Lukas on 2014.11.30.
 */
public class FriendsRecipesList {
    private LinkedList<FriendsRecipePreview> recipes = new LinkedList<>();

    public void addRecipe(FriendsRecipePreview recipe) {
        recipes.add(recipe);
    }

    public LinkedList<FriendsRecipePreview> getRecipes() {
        return recipes;
    }
}
