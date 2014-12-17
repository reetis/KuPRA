package eu.komanda30.kupra.controllers.frontpage;

import eu.komanda30.kupra.controllers.recipelist.RecipePreview;
import eu.komanda30.kupra.controllers.recipelist.RecipesList;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.Menus;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Controller
@RequestMapping("/")
public class FrontpageController {

    @Resource
    private Menus menus;

    @Resource
    private KupraUsers kupraUsers;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public String show(final RecipesList list) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        KupraUser user = kupraUsers.findByUsername(auth.getName());

        Iterable<Object> allRecipes = menus.findMostPopular(new PageRequest(0, 10));

        for (Object queryResult : allRecipes) {
            Recipe r = (Recipe) ((Object[]) queryResult)[0];
            RecipePreview recipePreview = new RecipePreview();
            recipePreview.setName(r.getName());
            recipePreview.setDescription(r.getDescription());
            recipePreview.setPublicAccess(r.isPublicAccess());
            recipePreview.setAccessible(user == r.getAuthor());
            recipePreview.setRecipeId(r.getRecipeId());

            if (!r.getRecipeImages().isEmpty()) {
                recipePreview.setRecipeImage(r.getRecipeImages().get(0));
            }

            list.addRecipe(recipePreview);
        }
        return "frontpage";
    }
}
