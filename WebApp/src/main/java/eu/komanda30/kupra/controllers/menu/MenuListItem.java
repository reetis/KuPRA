package eu.komanda30.kupra.controllers.menu;


import java.util.Date;

/**
 * Created by Ignas on 12/15/2014.
 */
public class MenuListItem {

    private Date dateTime;

    private String recipeName;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
