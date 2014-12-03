package eu.komanda30.kupra.controllers.fridge;

import eu.komanda30.kupra.entity.Fridge;
import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.repositories.Fridges;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Units;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

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


    @RequestMapping(method = RequestMethod.GET)
    public String showFridgeContent(final FridgeItemsList list) {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String username = auth.getName();
        UserId loggedUserId = UserId.forUsername(username);
        Iterable<Fridge> allFridges = fridges.findAllByUserId(UserId.forUsername(auth.getName()));
    for(Fridge f : allFridges){


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
}
