package eu.komanda30.kupra.controllers.recipelist;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.Recipes;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;

/**
 * Created by Rytis on 2014-11-27
 */
@Controller
@RequestMapping("/recipes")
public class RecipeListController {
    @Resource
    private Recipes recipes;

    @Resource
    private KupraUsers kupraUsers;

    @RequestMapping(method = RequestMethod.GET)
    public String showRecipesDefault(final RecipesList list, Model model) {
        return showRecipes("all", list, model);
    }

    @RequestMapping(value = "/{scope}", method = RequestMethod.GET)
    public String showRecipes(@PathVariable String scope, final RecipesList list, Model model) {
        Iterable<Recipe> allRecipes;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        KupraUser user = kupraUsers.findByUsername(auth.getName());
        switch (scope) {
            case "all":
                allRecipes = recipes.findAllAccessible(user, new PageRequest(0, 100));
                break;
            case "personal":
                allRecipes = recipes.findByUser(user, new PageRequest(0, 100));
                break;
            case "friends":
                allRecipes = recipes.findFriendsRecipes(user, new PageRequest(0, 100));
                break;
            default:
                throw new ResourceNotFoundException();
        }
        for (Recipe r: allRecipes) {
            RecipePreview recipePreview = new RecipePreview();
            recipePreview.setName(r.getName());
            recipePreview.setDescription(r.getDescription());
            recipePreview.setPublicAccess(r.isPublicAccess());
            recipePreview.setAccessible(user == r.getAuthor());
            recipePreview.setRecipeId(r.getRecipe_id());
            list.addRecipe(recipePreview);
        }
        model.addAttribute("section", scope);
        return "recipe-list";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {}
}
