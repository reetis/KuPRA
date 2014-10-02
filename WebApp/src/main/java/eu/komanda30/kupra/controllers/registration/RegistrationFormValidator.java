package eu.komanda30.kupra.controllers.registration;

import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.UsernamePasswordAuths;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationFormValidator implements Validator {
    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private UsernamePasswordAuths usernamePasswordAuths;

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
        if (usernamePasswordAuths.findOne(form.getUsername()) != null) {
            errors.rejectValue("login","AlreadyUsed");
        }
        if (kupraUsers.findByEmail(form.getEmail()) != null) {
            errors.rejectValue("email","AlreadyUsed");
        }
    }
}
