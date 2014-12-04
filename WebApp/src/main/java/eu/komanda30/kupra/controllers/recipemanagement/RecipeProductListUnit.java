package eu.komanda30.kupra.controllers.recipemanagement;

import eu.komanda30.kupra.entity.Product;

/**
 * Created by Ignas on 12/3/2014.
 */
public class RecipeProductListUnit {

    private Product product;

    private Double quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
