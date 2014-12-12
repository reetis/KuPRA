package eu.komanda30.kupra.controllers.menu;

import java.util.Date;

/**
 * Created by Gintare on 2014-12-07.
 */
public class NewMenuItemForm {

    private int recipe_id;

    private Date date_time;

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }
}
