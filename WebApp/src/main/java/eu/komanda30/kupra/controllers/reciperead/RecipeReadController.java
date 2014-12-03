package eu.komanda30.kupra.controllers.reciperead;

import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.repositories.Recipes;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/recipe")
public class RecipeReadController {

    @Resource
    private Recipes recipes;

    private static final Logger LOG = LoggerFactory.getLogger(RecipeReadController.class);

    @RequestMapping(value = "/read/{recipeId}", method = RequestMethod.GET)
    public String readRecipe(final RecipeReadForm form,
                           @PathVariable Integer recipeId){
        Recipe recipe = recipes.findOne(recipeId);
        form.setName(recipe.getName());
        form.setServings(recipe.getServings());
        form.setCookingTime(recipe.getCookingTime());
        form.setDescription(recipe.getDescription());
        form.setPublicAccess(recipe.isPublicAccess());
        form.setProcessDescription(recipe.getProcessDescription());

        return "recipeRead";
    }
}
