package eu.komanda30.kupra.controllers.menu;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Ignas on 12/16/2014.
 */
public class RecipeCookForm {
    private String name;
    private Integer recipeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dateTime;
    private Integer menuItemId;
    private Integer servings;
    private Integer score;

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

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
