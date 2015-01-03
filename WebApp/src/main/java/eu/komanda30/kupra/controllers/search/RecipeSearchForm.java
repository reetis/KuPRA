package eu.komanda30.kupra.controllers.search;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearchForm {
    private String title;
    private Boolean fromFridge;
    private List<Recipe> recipes = new ArrayList<>();

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getFromFridge() {
        return fromFridge;
    }

    public void setFromFridge(Boolean fromFridge) {
        this.fromFridge = fromFridge;
    }

    public static class Recipe {
        private String imageUrl;
        private String name;
        private String description;
        private int recipeId;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(int recipe_id) {
            this.recipeId = recipe_id;
        }
    }
}
