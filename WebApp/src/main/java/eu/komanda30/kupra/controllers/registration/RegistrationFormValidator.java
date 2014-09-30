package eu.komanda30.kupra.controllers.registration;

import eu.komanda30.kupra.services.UserRegistrar;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationFormValidator implements Validator {
    @Resource
    private UserRegistrar userRegistrar;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final RegistrationForm form = (RegistrationForm)target;
        if (!form.getPassword().equals(form.getPasswordRepeat())) {
            errors.rejectValue("passwordRepeat", "DoesNotMatch");
        }
        if (userRegistrar.isLoginUsed(form.getLogin())) {
            errors.rejectValue("login","AlreadyUsed");
        }
        if (userRegistrar.isEmailUsed(form.getEmail())) {
            errors.rejectValue("email","AlreadyUsed");
        }
    }
}
