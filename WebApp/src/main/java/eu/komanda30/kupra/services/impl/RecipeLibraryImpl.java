package eu.komanda30.kupra.services.impl;

import eu.komanda30.kupra.controllers.recipemanagement.RecipeManagementForm;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.repositories.Recipes;
import eu.komanda30.kupra.services.RecipeLibrary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by Ignas on 10/23/2014.
 */
@Service
public class RecipeLibraryImpl implements RecipeLibrary {

        @Resource
        private Recipes recipes;

        @Transactional
        @Override
        public void addRecipe(RecipeManagementForm recipeForm) {
            Recipe recipe = new Recipe();

            /* Cia turetu but apdorotas Kurimas */
            recipes.save(recipe);
        }

}
