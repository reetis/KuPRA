package eu.komanda30.kupra.controllers.fridge;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Lukas on 2014.12.03.
 */
public class FridgeAddItemForm {

    @NotNull
    private Double amount;

    @Min(0)
    private int selectedItemId;

    private String action;

    private String itemName;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getSelectedItemId() {
        return selectedItemId;
    }

    public void setSelectedItemId(int selectedItemId) {
        this.selectedItemId = selectedItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
