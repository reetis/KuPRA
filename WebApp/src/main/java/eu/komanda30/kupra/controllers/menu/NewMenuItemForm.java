package eu.komanda30.kupra.controllers.menu;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Gintare on 2014-12-07.
 */
public class NewMenuItemForm {

    private int recipeId;

    private String recipeName;

    private int servings;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dateTime;

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
