package eu.komanda30.kupra.controllers.unit;

import eu.komanda30.kupra.repositories.Units;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
            errors.rejectValue("name","AlreadyUsed", new Object[] {form.getName()}, null);
        }
        if (units.findByAbbreviation(form.getAbbreviation()) != null) {
            errors.rejectValue("abbreviation","AlreadyUsed", new Object[] {form.getAbbreviation()}, null);
        }
    }
}
