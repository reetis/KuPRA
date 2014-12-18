package eu.komanda30.kupra.controllers.menu;


import java.util.Date;

/**
 * Created by Ignas on 12/15/2014.
 */
public class MenuListItem {

    private Date dateTime;

    private String recipeName;

    private Integer recipeId;

    private Integer menuItemId;

    private Boolean cooked;

    public Boolean getCooked() {
        return cooked;
    }

    public void setCooked(Boolean cooked) {
        this.cooked = cooked;
    }

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

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
