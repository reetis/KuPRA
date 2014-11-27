package eu.komanda30.kupra.services.impl;

import eu.komanda30.kupra.controllers.recipemanagement.RecipeManagementForm;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.repositories.Recipes;
import eu.komanda30.kupra.services.RecipeLibrary;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RecipeLibraryImpl implements RecipeLibrary {
    private static final Logger LOG = LoggerFactory.getLogger(RecipeLibraryImpl.class);

    @Resource
    private Recipes recipes;

    @Transactional
    @Override
    public void addRecipe(RecipeManagementForm recipeForm) {
        Recipe recipe = new Recipe();

        recipe.setName(recipeForm.getName());
        recipe.setDescription(recipeForm.getDescription());
        recipe.setProcessDescription(recipeForm.getProcessDescription());
        recipe.setCookingTime(recipeForm.getCookingTime());
        recipe.setPublicAccess(recipeForm.isPublicAccess());
        recipe.setServings(recipeForm.getServings());

        recipes.save(recipe);
    }
}
