package eu.komanda30.kupra.controllers.search;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.entity.RecipeImage;
import eu.komanda30.kupra.entity.UserProfileImage;
import eu.komanda30.kupra.repositories.Friendships;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.services.RecipeFinder;
import eu.komanda30.kupra.services.UserFinder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Controller
@RequestMapping("/search")
public class SearchController {
    public static final int USER_MAX_RESULTS = 3;
    public static final int RECIPE_MAX_RESULTS = 3;
    @Resource
    private UserFinder userFinder;

    @Resource
    private RecipeFinder recipeFinder;

    @Resource
    private Friendships friendships;

    @Resource
    private KupraUsers kupraUsers;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public String showForm(@ModelAttribute("results") final InlineSearchResultForm form,
                           @RequestParam("query") final String query) {
        form.setQuery(query);

        userFinder.searchForUsers(query, USER_MAX_RESULTS).stream()
                .map(this::userToResultRow)
                .forEach(form::addPersonRow);

        recipeFinder.searchForRecipes(query, RECIPE_MAX_RESULTS).stream()
                .map(this::recipeToResultRow)
                .forEach(form::addRecipeRow);

        return "inline-search::searchResults";
    }

    @Transactional
    @RequestMapping(value = "/users/{query}", method = RequestMethod.GET)
    public String searchUsers(@PathVariable String query,
                              final UserSearchForm userSearchForm) {

        userSearchForm.setTitle(query);

        userFinder.searchForUsers(query).stream()
                .map(this::userToResultUser)
                .forEach(userSearchForm::addUser);

        return "search-user";
    }

    @Transactional
    @RequestMapping(value = "/recipes/{query}", method = RequestMethod.GET)
    public String searchRecipes(@PathVariable String query,
                              final RecipeSearchForm recipeSearchForm) {

        recipeSearchForm.setTitle(query);
        recipeSearchForm.setFromFridge(false);

        recipeFinder.searchForRecipes(query).stream()
                .map(this::recipeToResultRecipe)
                .forEach(recipeSearchForm::addRecipe);

        return "search-recipe";
    }

    private InlineSearchResultForm.PersonRow userToResultRow(KupraUser user) {
        final InlineSearchResultForm.PersonRow row = new InlineSearchResultForm.PersonRow();
        row.setName(user.getProfile().getName());
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        if (friendships.isFriends(kupraUser, user)
                || user.equals(kupraUser)) {
            row.setName(user.getProfile().getFullName());
            row.setImageUrl(user.getProfile().getMainPhoto()
                    .map(UserProfileImage::getThumbUrl)
                    .orElse(null));
        } else {
            row.setName(user.getUserId());
        }

        row.setUserId(user.getUserId());
        return row;
    }
    private UserSearchForm.User userToResultUser(KupraUser user) {
        final UserSearchForm.User row = new UserSearchForm.User();
        row.setName(user.getProfile().getName());
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        if (friendships.isFriends(kupraUser, user)
                || user.equals(kupraUser)) {
            row.setName(user.getProfile().getFullName());
            row.setImageUrl(user.getProfile().getMainPhoto()
                    .map(UserProfileImage::getThumbUrl)
                    .orElse(null));
        } else {
            row.setName(user.getUserId());
        }

        row.setUserId(user.getUserId());
        return row;
    }

    private InlineSearchResultForm.RecipeRow recipeToResultRow(Recipe recipe) {
        final InlineSearchResultForm.RecipeRow row = new InlineSearchResultForm.RecipeRow();
        row.setName(recipe.getName());
        row.setDescription(recipe.getDescription());
        row.setImageUrl(recipe.getMainRecipeImage()
                .map(RecipeImage::getThumbUrl)
                .orElse(null));
        row.setRecipeId(recipe.getRecipeId());
        return row;
    }

    private RecipeSearchForm.Recipe recipeToResultRecipe(Recipe recipe) {
        final RecipeSearchForm.Recipe row = new RecipeSearchForm.Recipe();
        row.setName(recipe.getName());
        row.setDescription(recipe.getDescription());
        row.setImageUrl(recipe.getMainRecipeImage()
                .map(RecipeImage::getThumbUrl)
                .orElse(null));
        row.setRecipeId(recipe.getRecipeId());
        return row;
    }
}
