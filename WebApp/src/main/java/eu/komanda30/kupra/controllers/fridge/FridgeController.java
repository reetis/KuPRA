package eu.komanda30.kupra.controllers.fridge;

import eu.komanda30.kupra.entity.FridgeItem;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.Products;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/fridge")
@Controller
public class FridgeController {
    @Resource
    private Products products;

    @Resource
    private KupraUsers kupraUsers;

    @Transactional
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteFridgeItem(
            @RequestParam(value = "product_id") Integer productId) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        kupraUser.removeFromFridgeByProduct(productId);
        kupraUsers.save(kupraUser);

        return "redirect:/fridge";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public String showFridgeContent(final FridgeItemsList list, final FridgeAddItemForm fridgeAddItemForm) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        populateFridgeItemsList(list, kupraUser);

        return "fridge";
    }

    private void populateFridgeItemsList(FridgeItemsList list, KupraUser kupraUser) {
        kupraUser.getFridgeItems().parallelStream()
                .map(this::makeFridgeItemForm)
                .forEach(list::addItem);
    }

    @ModelAttribute("products")
    public Iterable<Product> getProducts() {
        return this.products.findAll();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid final FridgeAddItemForm form, final FridgeItemsList list,
                         final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fridge";
        }

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        final Product product = products.findOne(form.getSelectedProductId());
        kupraUser.addFridgeItem(product, form.getAmount());
        kupraUsers.save(kupraUser);

        populateFridgeItemsList(list, kupraUser);

        return "fridge";
    }

    private FridgeItemForm makeFridgeItemForm(FridgeItem f) {
        final FridgeItemForm fridgeItemForm = new FridgeItemForm();
        final Product product = f.getProduct();
        fridgeItemForm.setName(product.getName());
        fridgeItemForm.setUnit(product.getUnit().getAbbreviation());
        fridgeItemForm.setAmount(f.getAmount());
        fridgeItemForm.setProductId(product.getId());
        return fridgeItemForm;
    }
}
