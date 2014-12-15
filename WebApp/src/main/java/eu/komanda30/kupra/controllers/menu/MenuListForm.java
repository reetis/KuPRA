package eu.komanda30.kupra.controllers.menu;

import java.util.ArrayList;

/**
 * Created by Ignas on 12/15/2014.
 */
public class MenuListForm {
    private ArrayList<MenuListItem> menulistItems = new ArrayList<MenuListItem>();

    public ArrayList<MenuListItem> getMenulistItems() {
        return menulistItems;
    }

    public void setMenulistItems(ArrayList<MenuListItem> menulistItems) {
        this.menulistItems = menulistItems;
    }

    public void addMenuListItem(MenuListItem menuListItem){
        this.menulistItems.add(menuListItem);
    }
}
