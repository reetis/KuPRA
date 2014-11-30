package eu.komanda30.kupra.controllers.product;

import eu.komanda30.kupra.controllers.unit.NewUnitForm;
import eu.komanda30.kupra.repositories.Products;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

/**
 * Created by Gintare on 2014-11-27.
 */
@Component
public class NewProductValidator implements Validator {
    @Resource
    private Products products;
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        final NewProductForm form = (NewProductForm)target;
        if (products.findByName(form.getName()) != null) {
            errors.rejectValue("name","AlreadyUsed", new Object[] {form.getName()}, null);
        }
    }
}