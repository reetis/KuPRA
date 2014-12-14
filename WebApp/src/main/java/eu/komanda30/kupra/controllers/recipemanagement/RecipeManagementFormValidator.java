package eu.komanda30.kupra.controllers.recipemanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RecipeManagementFormValidator implements Validator {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeManagementController.class);

    @Override
    public boolean supports(Class<?> clazz) {
            return RecipeManagementForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
