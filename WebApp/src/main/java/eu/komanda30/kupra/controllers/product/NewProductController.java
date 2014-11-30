package eu.komanda30.kupra.controllers.product;

//import eu.komanda30.kupra.controllers.editprofile.EditProfileForm;
import eu.komanda30.kupra.controllers.product.NewProductValidator;
import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Units;
import eu.komanda30.kupra.services.UserRegistrar;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Gintare on 2014-11-27.
 */
@Controller
@RequestMapping("/product")
public class NewProductController {

    @Resource
    private NewProductValidator newProductValidator;
    @Resource
    private Units units;
    @Resource
    private Products products;

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.addValidators(newProductValidator);
//    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String showUnitFrame(final NewProductForm form) {
        return "newProduct :: newProductForm";
    }

    @ModelAttribute("units")
    public Iterable<Unit> getUnits() {
        return this.units.findAll();
    }
    @Transactional
    @RequestMapping(value="add", method = RequestMethod.POST)
    public String submit(@Valid final NewProductForm form,
                         final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newProduct :: newProductForm";
        }
        final Product newProductEntity = new Product();
        newProductEntity.setName(form.getName());
        newProductEntity.setDescription(form.getDescription());
        newProductEntity.setSelectedUnit(form.getSelectedUnitId());
        products.save(newProductEntity);
        return "newProduct :: productSavedForm";
    }

}



