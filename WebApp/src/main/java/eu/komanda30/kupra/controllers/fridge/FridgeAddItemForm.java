package eu.komanda30.kupra.controllers.fridge;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class FridgeAddItemForm {

    @Min(0)
    private int selectedProductId;

    @DecimalMin(value = "0", inclusive = false)
    @DecimalMax(value = "10000")
    @Digits(integer = 4, fraction = 2)
    @NotNull
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getSelectedProductId() {
        return selectedProductId;
    }

    public void setSelectedProductId(int selectedProductId) {
        this.selectedProductId = selectedProductId;
    }
}
