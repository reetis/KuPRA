package eu.komanda30.kupra.controllers.recipelist;

import eu.komanda30.kupra.entity.RecipeImage;

public class RecipePreview {
    private String name;

    private boolean publicAccess;

    private boolean accessible;

    private String description;

    private int recipeId;

    private RecipeImage recipeImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublicAccess() {
        return publicAccess;
    }

    public void setPublicAccess(boolean publicAccess) {
        this.publicAccess = publicAccess;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public RecipeImage getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(RecipeImage recipeImage) {
        this.recipeImage = recipeImage;
    }
}
