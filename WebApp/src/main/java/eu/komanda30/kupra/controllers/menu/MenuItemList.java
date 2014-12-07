package eu.komanda30.kupra.controllers.menu;

import java.util.LinkedList;

/**
 * Created by Gintare on 2014-12-07.
 */
public class MenuItemList {
    private LinkedList<MenuItemForm> items = new LinkedList<MenuItemForm>();

    public void addItem(MenuItemForm item) {
        items.add(item);
    }

    public LinkedList<MenuItemForm> getItems() {
        return items;
    }
}
