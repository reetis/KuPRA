package eu.komanda30.kupra.controllers.recipemanagement;

import java.io.Serializable;

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

    public void increaseQuantity(Double quantity) { this.quantity += quantity; }
}
