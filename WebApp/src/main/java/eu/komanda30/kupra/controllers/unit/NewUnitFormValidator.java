package eu.komanda30.kupra.controllers.unit;

import eu.komanda30.kupra.controllers.registration.RegistrationForm;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.Units;
import eu.komanda30.kupra.repositories.UsernamePasswordAuths;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

/**
 * Created by Rytis on 2014-10-21.
 */

@Component
public class NewUnitFormValidator implements Validator {
    @Resource
    private Units units;

    @Override
    public boolean supports(Class<?> clazz) {
        return NewUnitForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final NewUnitForm form = (NewUnitForm)target;
        if (units.findByName(form.getName()) != null) {
            errors.rejectValue("name","AlreadyUsed");
        }
        if (units.findByAbbrevation(form.getAbbrevation()) != null) {
            errors.rejectValue("abbrevation","AlreadyUsed");
        }
    }
}
