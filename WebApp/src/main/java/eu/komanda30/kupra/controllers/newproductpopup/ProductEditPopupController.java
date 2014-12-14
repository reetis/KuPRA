package eu.komanda30.kupra.controllers.newproductpopup;

import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Units;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/product-edit-popup")
public class ProductEditPopupController {

    @Resource
    private ProductEditPopupFormValidator newProductValidator;
    @Resource
    private Units units;
    @Resource
    private Products products;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(newProductValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showForm(final NewProductPopupForm form) {
        return "popups/productEdit :: productEditForm";
    }

    @ModelAttribute("units")
    public Iterable<Unit> getUnits() {
        return this.units.findAll();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(@Valid final NewProductPopupForm form,
                             final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "popups/productEdit :: productEditForm";
        }
        final Product newProductEntity = new Product();
        newProductEntity.setName(form.getName());
        newProductEntity.setDescription(form.getDescription());
        newProductEntity.setUnit(units.findOne(form.getSelectedUnitId()));
        products.save(newProductEntity);

        final Unit unit = units.findOne(form.getSelectedUnitId());
        form.setUnitName(unit.getName());
        return "popups/productEdit :: productSavedForm";
    }

}



