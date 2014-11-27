package eu.komanda30.kupra.controllers.friendship.friends_list;

import eu.komanda30.kupra.controllers.recipemanagement.RecipeManagementForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Ignas on 11/27/2014.
 */
@Controller
@RequestMapping("/friends")
public class FriendsListController {

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public String showNewRecipeForm(final RecipeManagementForm form) {
        return "friends_list";
    }
}
