package eu.komanda30.kupra.controllers.recipemanagement;

import java.io.Serializable;
import java.math.BigDecimal;

public class RecipeProductListUnit implements Serializable {

    private int productId;
    private String productName;
    private BigDecimal quantity = BigDecimal.ZERO;
    private String unit;

    public RecipeProductListUnit(int productId, String productName, String unit) {
        this.productId = productId;
        this.productName = productName;
        this.unit = unit;
    }

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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(BigDecimal quantity) { this.quantity = this.quantity.add(quantity); }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
