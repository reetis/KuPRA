package eu.komanda30.kupra.controllers.resetpassword.changePassword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ResetPasswordChangeFormValidator implements Validator {
    public static final Logger LOG = LoggerFactory.getLogger(ResetPasswordChangeFormValidator.class);


    @Override
    public boolean supports(Class<?> clazz) {
        return ResetPasswordChangeForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final ResetPasswordChangeForm form = (ResetPasswordChangeForm)target;

        if (!form.getPassword().equals(form.getPasswordRepeat())) {
            errors.rejectValue("passwordRepeat", "DoesNotMatch");
        }
    }
}
