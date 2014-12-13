package eu.komanda30.kupra.controllers.resetpassword;

import eu.komanda30.kupra.repositories.KupraUsers;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ResetPasswordFormValidator implements Validator {
    @Resource
    private KupraUsers kupraUsers;

    @Override
    public boolean supports(Class<?> clazz) {
        return ResetPasswordForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final ResetPasswordForm form = (ResetPasswordForm) target;
        if (kupraUsers.findByEmail(form.getEmail()) == null) {
            errors.rejectValue("email", "NotFound");
        }
    }
}
