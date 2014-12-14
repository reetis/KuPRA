package eu.komanda30.kupra.controllers.newproductpopup;

import eu.komanda30.kupra.repositories.Products;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductEditPopupFormValidator implements Validator {
    @Resource
    private Products products;

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductEditForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final ProductEditForm form = (ProductEditForm)target;
        if (!form.isEditForm() && products.findByName(form.getName()) != null) {
            errors.rejectValue("name","AlreadyUsed", new Object[] {form.getName()}, null);
        }
    }
}
