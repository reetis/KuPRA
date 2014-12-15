package eu.komanda30.kupra.controllers.menu;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ignas on 12/15/2014.
 */
public class MenuListDay {
    private Date day;

    private ArrayList<MenuListItem> menuListItems = new ArrayList<MenuListItem>();

    public ArrayList<MenuListItem> getMenuListItems() {
        return menuListItems;
    }

    public void setMenuListItems(ArrayList<MenuListItem> menulistItems) {
        this.menuListItems = menulistItems;
    }

    public void addMenuListItem(MenuListItem menuListItem){
        this.menuListItems.add(menuListItem);
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }
}
