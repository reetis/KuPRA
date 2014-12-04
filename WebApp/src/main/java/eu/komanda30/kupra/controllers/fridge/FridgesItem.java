package eu.komanda30.kupra.controllers.fridge;

/**
 * Created by Lukas on 2014.12.02.
 */
public class FridgesItem {
    private String name;
    private String unit;
    private double amount;
    private int itemId;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
