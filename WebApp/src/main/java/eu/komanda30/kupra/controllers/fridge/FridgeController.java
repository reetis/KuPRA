package eu.komanda30.kupra.controllers.fridge;

import eu.komanda30.kupra.controllers.recipelist.RecipePreview;
import eu.komanda30.kupra.controllers.recipelist.RecipesList;
import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Units;
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
    private Units units;


    @RequestMapping(method = RequestMethod.GET)
    public String showFridgeContent(final FridgeItemsList list) {

    Iterable<Product> allProducts = products.findAll();
    for(Product p : allProducts){


        Unit unit = units.findOne(p.getSelectedUnit());

        FridgesItem fridgesItem = new FridgesItem();
        fridgesItem.setName(p.getName());
        fridgesItem.setUnit(unit.getAbbreviation());
        fridgesItem.setAmount(1);
        list.addItem(fridgesItem);
    }

        return "fridge";
    }
}
