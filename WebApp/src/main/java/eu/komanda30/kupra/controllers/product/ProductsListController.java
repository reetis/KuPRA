package eu.komanda30.kupra.controllers.product;

import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Recipes;
import eu.komanda30.kupra.repositories.Units;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/product-list")
public class ProductsListController {

    @Resource
    private Products products;

    @Resource
    private Recipes recipes;

    @Resource
    private Units units;

    @RequestMapping(method = RequestMethod.GET)
    public String showProducts(final ProductsListForm list) {
        Iterable<Product> allProducts = products.findAll();
        for(Product p : allProducts){
            final ProductItem fridgesItem = new ProductItem();
            fridgesItem.setIsUsed(products.isUsedInFridge(p) || products.isUsedInRecipes(p));
            fridgesItem.setProductId(p.getId());
            fridgesItem.setName(p.getName());
            fridgesItem.setUnit(p.getUnit().getAbbreviation());
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







