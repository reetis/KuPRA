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
            final FridgeItemsList list, final FridgeAddItemForm fridgeAddItemForm,
            @RequestParam(value = "product_id") Integer productId) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        kupraUser.removeFromFridgeByProduct(productId);
        kupraUsers.save(kupraUser);

        populateFridgeItemsList(list, kupraUser);

        return "fridge :: table-body";
    }

    private void populateFridgeItemsList(FridgeItemsList list, KupraUser kupraUser) {
        kupraUser.getFridgeItems().stream()
                .map(this::makeFridgeItemForm)
                .sorted( (x, y) -> (x.getName().compareToIgnoreCase(y.getName())) )
                .forEach(list::addItem);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public String showFridgeContent(final FridgeItemsList list, final FridgeAddItemForm fridgeAddItemForm) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        populateFridgeItemsList(list, kupraUser);

        return "fridge";
    }

    @ModelAttribute("products")
    public Iterable<Product> getProducts() {
        return this.products.findByOrderByNameAsc();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String addItem(@Valid final FridgeAddItemForm form,
                          final BindingResult bindingResult,
                          final FridgeItemsList list) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        if (bindingResult.hasErrors()) {
            populateFridgeItemsList(list, kupraUser);
            return "fridge :: table-body";
        }

        final Product product = products.findOne(form.getSelectedProductId());
        kupraUser.addFridgeItem(product, form.getAmount());
        kupraUsers.save(kupraUser);

        populateFridgeItemsList(list, kupraUser);
        return "fridge :: table-body";
    }

    private FridgeItemForm makeFridgeItemForm(FridgeItem f) {
        final FridgeItemForm fridgeItemForm = new FridgeItemForm();
        final Product product = f.getProduct();
        fridgeItemForm.setName(product.getName());
        fridgeItemForm.setUnit(product.getUnit().getAbbreviation());
        fridgeItemForm.setAmount(f.getAmount().stripTrailingZeros());
        fridgeItemForm.setProductId(product.getId());
        return fridgeItemForm;
    }
}
