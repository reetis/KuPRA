package eu.komanda30.kupra.controllers.fridge;

import eu.komanda30.kupra.entity.Fridge;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.repositories.Fridges;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Units;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Lukas on 2014.12.02.
 */

@RequestMapping("/fridge")
@Controller
public class FridgeController {

    @Resource
    private Products products;

    @Resource
    private Fridges fridges;

    @Resource
    private Units units;

    @Resource
    private KupraUsers kupraUsers;


    @RequestMapping(method = RequestMethod.GET)
    public String showFridgeContent(final FridgeItemsList list, final FridgeAddItemForm fridgeAddItemForm) {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String username = auth.getName();
        KupraUser kupraUser = kupraUsers.findByUsername(username);

        List<Fridge> fridgesList = kupraUser.getFridges();

        for (Fridge f : fridgesList) {

            Product product = products.findOne(f.getProductId());
            Unit unit = units.findOne(product.getSelectedUnit());

            FridgesItem fridgesItem = new FridgesItem();
            fridgesItem.setName(product.getName());
            fridgesItem.setUnit(unit.getAbbreviation());
            fridgesItem.setAmount(f.getAmount());
            list.addItem(fridgesItem);
        }


        return "fridge";
    }

    @ModelAttribute("products")
    public Iterable<Product> getProducts() {
        return this.products.findAll();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid final FridgeAddItemForm form, final FridgeItemsList list,
                         final BindingResult bindingResult) {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());
        if (bindingResult.hasErrors()) {
            return "fridge";
        }

        final Fridge newFridge = new Fridge();
        newFridge.setProductId(form.getSelectedItemId());
        newFridge.setAmount(form.getAmount());
        newFridge.setUser(kupraUser);

        fridges.save(newFridge);

        Product product = products.findOne(form.getSelectedItemId());
        form.setItemName(product.getName());


        final String username = auth.getName();


        List<Fridge> fridgesList = kupraUser.getFridges();

        for (Fridge f : fridgesList) {

            Product productt = products.findOne(f.getProductId());
            Unit unit = units.findOne(productt.getSelectedUnit());

            FridgesItem fridgesItem = new FridgesItem();
            fridgesItem.setName(productt.getName());
            fridgesItem.setUnit(unit.getAbbreviation());
            fridgesItem.setAmount(f.getAmount());
            list.addItem(fridgesItem);
        }

        return "fridge";
    }
}
