package eu.komanda30.kupra.controllers.menu;

import java.util.Date;

/**
 * Created by Ignas on 12/16/2014.
 */
public class RecipeCookForm {
    private String name;
    private Date dateTime;
    private Integer menuItemId;

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
