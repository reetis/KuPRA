package eu.komanda30.kupra.controllers.product;

import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.repositories.Products;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequestMapping("/products")
public class ProductsListController {
    @Resource
    private Products products;

    @RequestMapping(method = RequestMethod.GET)
    public String showProducts() {
        return "product-list";
    }

    @ResponseBody
    @RequestMapping(value="/{product_id}", method = RequestMethod.DELETE)
    public String deleteUnit(@PathVariable("product_id") int productId) {
        final Product product = products.findOne(productId);
        if (product != null){
            if (products.isUsedInRecipes(product) || products.isUsedInFridge(product)){
                return "false";
            }else{
                products.delete(product);
            }
        }

        return "";
    }

    @ModelAttribute("products")
    public List<ProductItem> getAllProducts() {
        return StreamSupport.stream(products.findAll().spliterator(), false)
                .map(p -> {
                    final ProductItem fridgesItem = new ProductItem();
                    fridgesItem.setIsUsed(products.isUsedInFridge(p) || products.isUsedInRecipes(p));
                    fridgesItem.setProductId(p.getId());
                    fridgesItem.setName(p.getName());
                    fridgesItem.setUnit(p.getUnit().getAbbreviation());
                    fridgesItem.setDescription(p.getDescription());
                    return fridgesItem;
                }).collect(Collectors.toList());
    }
}







