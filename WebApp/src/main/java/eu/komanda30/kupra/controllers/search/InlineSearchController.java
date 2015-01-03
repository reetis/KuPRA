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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Controller
@RequestMapping("/search")
public class InlineSearchController {
    public static final int USER_MAX_RESULTS = 2;
    public static final int RECIPE_MAX_RESULTS = 2;
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
        userFinder.searchForUsers(query, USER_MAX_RESULTS).stream()
                .map(this::userToResultRow)
                .forEach(form::addPersonRow);

        recipeFinder.searchForRecipes(query, RECIPE_MAX_RESULTS).stream()
                .map(this::recipeToResultRow)
                .forEach(form::addRecipeRow);

        return "inline-search::searchResults";
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
}
