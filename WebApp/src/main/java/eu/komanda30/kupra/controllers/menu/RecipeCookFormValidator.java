package eu.komanda30.kupra.controllers.menu;

import eu.komanda30.kupra.repositories.Recipes;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;


@Component
public class RecipeCookFormValidator implements Validator {

    @Resource
    private Recipes recipes;

    @Override
    public boolean supports(Class<?> aClass) {
        return RecipeCookForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RecipeCookForm recipeCookForm = (RecipeCookForm) target;

        if (recipes.findOne(recipeCookForm.getRecipeId()) == null) {
            errors.rejectValue("recipeId", "RecipeNotFound");
        }

        if (recipeCookForm.getServings() < 1) {
            errors.rejectValue("servings", "WrongServingsQty");
        }

        if (recipeCookForm.getScore() < 1 || recipeCookForm.getScore() > 10) {
            errors.rejectValue("score", "WrongScore");
        }


    }

}
