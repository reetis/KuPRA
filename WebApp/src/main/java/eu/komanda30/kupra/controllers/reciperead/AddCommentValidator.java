package eu.komanda30.kupra.controllers.reciperead;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddCommentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AddCommentForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        validateComment((AddCommentForm) target, errors);
    }

    private void validateComment(AddCommentForm form, Errors errors) {

        if (form.getComment().length() > 256) {
            errors.rejectValue("comment", "Too long");
        }
    }
}
