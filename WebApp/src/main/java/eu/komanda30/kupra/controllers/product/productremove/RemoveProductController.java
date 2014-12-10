package eu.komanda30.kupra.controllers.product.productremove;

import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Recipes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Ignas on 12/7/2014.
 */
@Controller
@RequestMapping("/product")
public class RemoveProductController {

        @Resource
        private Products products;

        @Resource
        private Recipes recipes;

        @ResponseBody
        @RequestMapping(value="/delete", method = RequestMethod.POST)
        public String deleteUnit(@RequestParam("product_id") Integer unitId) {
            Product product = products.findOne(unitId);
            if (product != null){
                if (products.isUsedInRecipes(product) || products.isUsedInFridge(product)){
                    return "false";
                }else{
                    products.delete(product);
                }
            }

            return "";
        }
}
