package eu.komanda30.kupra.services;

import eu.komanda30.kupra.controllers.recipemanagement.RecipeManagementForm;
import eu.komanda30.kupra.entity.UserId;

public interface RecipeLibrary {
    public void addRecipe(RecipeManagementForm recipeForm, UserId author);
}
