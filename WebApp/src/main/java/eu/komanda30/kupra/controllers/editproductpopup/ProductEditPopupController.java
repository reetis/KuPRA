package eu.komanda30.kupra.controllers.editproductpopup;

import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Units;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/product-edit-popup")
public class ProductEditPopupController {

    @Resource
    private ProductEditFormValidator newProductValidator;

    @Resource
    private Units units;

    @Resource
    private Products products;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(newProductValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showNewProductForm(final ProductEditForm form) {
        return "popups/product :: productEditForm";
    }

    @RequestMapping(value="/{productId}",method = RequestMethod.GET)
    @Transactional
    public String showEditProductForm(
            @PathVariable("productId") int productId,
            final ProductEditForm form) {
        final Product product = products.findOne(productId);
        Assert.notNull(product);

        form.setProductId(productId);
        form.setName(product.getName());
        form.setDescription(product.getDescription());
        form.setSelectedUnitId(product.getUnit().getId());
        form.setUnitName(product.getUnit().getName());

        return "popups/product :: productEditForm";
    }

    @ModelAttribute("units")
    public Iterable<Unit> getUnits() {
        return this.units.findAllOrderByNameAsc();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(@Valid final ProductEditForm form,
                             final BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "popups/product :: productEditForm";
        }

        final Product product;
        if (form.isEditForm()) {
            Assert.state(request.isUserInRole("ROLE_ADMIN"));
            product = products.findOne(form.getProductId());
        } else {
            product = new Product();
        }

        product.setName(form.getName());
        product.setDescription(form.getDescription());
        product.setUnit(units.findOne(form.getSelectedUnitId()));
        products.save(product);

        final Unit unit = units.findOne(form.getSelectedUnitId());
        form.setUnitName(unit.getName());
        return "popups/product :: productSavedForm";
    }

    @RequestMapping(value = "getSelectUnitForm", method = RequestMethod.GET)
    public String getSelectUnitForm() {
        return "popups/product :: selectUnitForm";
    }
}



