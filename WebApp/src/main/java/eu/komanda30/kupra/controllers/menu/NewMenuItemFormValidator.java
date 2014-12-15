package eu.komanda30.kupra.controllers.menu;

import eu.komanda30.kupra.repositories.Recipes;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Rytis on 2014-12-11.
 */
@Component
public class NewMenuItemFormValidator implements Validator {
    @Resource
    private Recipes recipes;

    @Override
    public boolean supports(Class<?> aClass) {
        return NewMenuItemForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final NewMenuItemForm form = (NewMenuItemForm)target;
        Date currentDate = new Date();

        if (recipes.findOne(form.getRecipeId()) == null) {
            errors.rejectValue("recipeId", "RecipeNotFound");
        }

        if (form.getServings() < 1) {
            errors.rejectValue("servings", "WrongServingsQty");
        }

        if (form.getDateTime().compareTo(currentDate) <= 0) {
            errors.rejectValue("dateTime", "DateBeforeNow");
        }
    }
}
