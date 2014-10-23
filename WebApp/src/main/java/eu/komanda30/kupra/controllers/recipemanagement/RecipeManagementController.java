package eu.komanda30.kupra.controllers.recipemanagement;

import eu.komanda30.kupra.controllers.registration.RegistrationFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Ignas on 10/23/2014.
 */
@Controller
public class RecipeManagementController {

    private RegistrationFormValidator registrationFormValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(registrationFormValidator);
    }

    @RequestMapping(value="/recipe_create", method = RequestMethod.GET)
    public String showNewRecipeForm(@Valid final RecipeManagementForm form,
                               final BindingResult bindingResult) {
        return "recipe_form";
    }

    @RequestMapping(value="/recipe_create", method = RequestMethod.POST)
    public String createRecipe(@Valid final RecipeManagementForm form,
                         final BindingResult bindingResult) {

        return "recipe_form";
    }
}
