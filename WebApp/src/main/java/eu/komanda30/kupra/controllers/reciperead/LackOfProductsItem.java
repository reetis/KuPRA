package eu.komanda30.kupra.controllers.reciperead;

import java.math.BigDecimal;

/**
 * Created by Ignas on 12/17/2014.
 */
public class LackOfProductsItem {

    private String name;
    private String unit;

    private BigDecimal amount;

    private Integer productId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
