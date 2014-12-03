package eu.komanda30.kupra.controllers.friendsrecipelist;

import eu.komanda30.kupra.entity.Friendship;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.repositories.Friendships;
import eu.komanda30.kupra.repositories.Recipes;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Lukas on 2014.11.30.
 */

@Controller
@RequestMapping("/recipes")
public class FriendsRecipeListController {

    @Resource
    private Friendships friendships;

    @Resource
    private Recipes recipes;

  /*  @RequestMapping(method = RequestMethod.GET)
    public String showRecipes(final FriendsRecipesList list) {
        Iterable<Recipe> allRecipes = recipes.findByPublic();
        for (Recipe r: allRecipes) {
            FriendsRecipePreview recipePreview = new FriendsRecipePreview();
            recipePreview.setName(r.getName());
            recipePreview.setDescription(r.getDescription());
            list.addRecipe(recipePreview);
        }
        return "recipe-list";
    }*/

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public String showPersonalRecipes(final FriendsRecipesList list) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String username = auth.getName();
        String loggedUserId = username;

        List<Friendship> friendshipsList = friendships.findFriendsOf(loggedUserId);

        for (Friendship friendship : friendshipsList) {

            KupraUser kupraUser = friendship.getFriendOf(loggedUserId);

            Iterable<Recipe> allRecipes = recipes.findByUser(kupraUser.getUserId());

            for (Recipe r: allRecipes) {
                FriendsRecipePreview recipePreview = new FriendsRecipePreview();
                recipePreview.setName(r.getName());
                recipePreview.setDescription(r.getDescription());
                list.addRecipe(recipePreview);
            }

        }




        return "recipe-list-friends";
    }
}
