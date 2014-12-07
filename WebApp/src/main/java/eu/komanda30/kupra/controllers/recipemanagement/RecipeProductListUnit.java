package eu.komanda30.kupra.controllers.recipemanagement;

import java.io.Serializable;

/**
 * Created by Ignas on 12/3/2014.
 */
public class RecipeProductListUnit implements Serializable {

    private int productId;
    private String productName;

    private Double quantity;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
