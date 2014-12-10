package eu.komanda30.kupra.controllers.search;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.entity.RecipeImage;
import eu.komanda30.kupra.entity.UserProfileImage;
import eu.komanda30.kupra.services.RecipeFinder;
import eu.komanda30.kupra.services.UserFinder;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class InlineSearchController {
    @Resource
    private UserFinder userFinder;

    @Resource
    private RecipeFinder recipeFinder;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public String showForm(@ModelAttribute("results") final InlineSearchResultForm form,
                           @RequestParam("query") final String query) {
        userFinder.searchForUsers(query).parallelStream()
                .map(this::userToResultRow)
                .forEach(form::addPersonRow);

        recipeFinder.searchForRecipes(query).parallelStream()
                .map(this::recipeToResultRow)
                .forEach(form::addRecipeRow);

        return "inline-search::searchResults";
    }

    private InlineSearchResultForm.PersonRow userToResultRow(KupraUser user) {
        final InlineSearchResultForm.PersonRow row = new InlineSearchResultForm.PersonRow();
        row.setName(user.getProfile().getName());
        row.setImageUrl(user.getProfile().getMainPhoto()
                .map(UserProfileImage::getThumbUrl)
                .orElse(null));
        return row;
    }

    private InlineSearchResultForm.RecipeRow recipeToResultRow(Recipe recipe) {
        final InlineSearchResultForm.RecipeRow row = new InlineSearchResultForm.RecipeRow();
        row.setName(recipe.getName());
        row.setDescription(recipe.getDescription());
        row.setImageUrl(recipe.getMainRecipeImage()
                .map(RecipeImage::getThumbUrl)
                .orElse(null));
        return row;
    }
}
