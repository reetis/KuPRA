package eu.komanda30.kupra.controllers.menu;

import java.util.ArrayList;

/**
 * Created by Ignas on 12/15/2014.
 */
public class MenuListForm {
    private ArrayList<MenuListDay> menuListDays = new ArrayList<>();

    public ArrayList<MenuListDay> getMenuListDays() {
        return menuListDays;
    }

    public void setMenuListDays(ArrayList<MenuListDay> menuListDays) {
        this.menuListDays = menuListDays;
    }

    public void addMenuListDay(MenuListDay menuListDay){
        this.menuListDays.add(menuListDay);
    }
}
