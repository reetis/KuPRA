package eu.komanda30.kupra.controllers.recipelist;

import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.repositories.Recipes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by Rytis on 2014-11-27
 */
@Controller
@RequestMapping("/recipes")
public class RecipeListController {
    @Resource
    private Recipes recipes;

    @RequestMapping(method = RequestMethod.GET)
    public String showRecipes(final RecipesList list) {
        Iterable<Recipe> allRecipes = recipes.findByPublic();
        for (Recipe r: allRecipes) {
            RecipePreview recipePreview = new RecipePreview();
            recipePreview.setName(r.getName());
            recipePreview.setDescription(r.getDescription());
            recipePreview.setPublicAccess(r.isPublicAccess());
            list.addRecipe(recipePreview);
        }
        return "recipe-list";
    }

    @RequestMapping(value = "/personal", method = RequestMethod.GET)
    public String showPersonalRecipes(final RecipesList list) {
        Iterable<Recipe> allRecipes = recipes.findAll();
        for (Recipe r: allRecipes) {
            RecipePreview recipePreview = new RecipePreview();
            recipePreview.setName(r.getName());
            recipePreview.setDescription(r.getDescription());
            recipePreview.setPublicAccess(r.isPublicAccess());
            list.addRecipe(recipePreview);
        }
        return "recipe-list-personal";
    }
}
