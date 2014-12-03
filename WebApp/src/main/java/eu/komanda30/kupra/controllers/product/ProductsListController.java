package eu.komanda30.kupra.controllers.product;

import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Units;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Gintare on 2014-12-02.
 */
@Controller
@RequestMapping("/product-list")
public class ProductsListController {

    @Resource
    private Products products;

    @Resource
    private Units units;

    @RequestMapping(method = RequestMethod.GET)
    public String showProducts(final ProductsListForm list) {
        Iterable<Product> allProducts = products.findAll();
        for(Product p : allProducts){


            Unit unit = units.findOne(p.getSelectedUnit());

            ProductItem fridgesItem = new ProductItem();
            fridgesItem.setName(p.getName());
            fridgesItem.setUnit(unit.getAbbreviation());
            fridgesItem.setDescription(p.getDescription());
            list.addItem(fridgesItem);
        }
        return "product-list";
    }

//    @ModelAttribute("products")
//    public Iterable<Product> getUnits() {
//        return this.
//    }
}






