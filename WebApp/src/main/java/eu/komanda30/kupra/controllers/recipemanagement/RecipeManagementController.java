package eu.komanda30.kupra.controllers.recipemanagement;

import eu.komanda30.kupra.services.RecipeLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by Ignas on 10/23/2014.
 */
@Controller
public class RecipeManagementController {
    private static final Logger LOG = LoggerFactory.getLogger(RecipeManagementController.class);

    @Resource
    private RecipeManagementFormValidator recipeManagementFormValidator;

    @Resource
    private RecipeLibrary recipeLibrary;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(recipeManagementFormValidator);
    }

    @RequestMapping(value="/recipe_create", method = RequestMethod.GET)
    public String showNewRecipeForm(final RecipeManagementForm form) {
        LOG.debug("IGNO LOGAS: ACCESSED GET METHOD<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        return "recipe_form";
    }

    @RequestMapping(value="/recipe_create", method = RequestMethod.POST)
    public String createRecipe(@Valid final RecipeManagementForm recipeManagementForm,
                         final BindingResult bindingResult) {
        LOG.debug("IGNO LOGAS: ACCESSED POST METHOD<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        if (bindingResult.hasErrors()){
            LOG.debug("IGNO LOGAS: ACCESSED EROORS <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        }
        LOG.debug(String.valueOf(bindingResult.getFieldError("name")));
        bindingResult.getFieldError("name");
        recipeLibrary.addRecipe(recipeManagementForm);
        return "recipe_form";
    }
}
