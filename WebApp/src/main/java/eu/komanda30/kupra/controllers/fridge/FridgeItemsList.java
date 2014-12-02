package eu.komanda30.kupra.controllers.fridge;

import java.util.LinkedList;

/**
 * Created by Lukas on 2014.12.02.
 */
public class FridgeItemsList {
    private LinkedList<FridgesItem> items = new LinkedList<FridgesItem>();

    public void addItem(FridgesItem item){
        items.add(item);
    }

    public LinkedList<FridgesItem> getItems(){
        return items;
    }
}
