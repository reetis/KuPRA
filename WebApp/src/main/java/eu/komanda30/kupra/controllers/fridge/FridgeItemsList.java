package eu.komanda30.kupra.controllers.fridge;

import java.util.LinkedList;

public class FridgeItemsList {
    private LinkedList<FridgeItemForm> items = new LinkedList<FridgeItemForm>();

    public void addItem(FridgeItemForm item) {
        items.add(item);
    }

    public LinkedList<FridgeItemForm> getItems() {
        return items;
    }
}
