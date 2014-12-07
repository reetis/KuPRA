package eu.komanda30.kupra.controllers.menu;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Gintare on 2014-12-07.
 */
public class MenuAddItemForm {

    @NotNull
    @Min(0)
    private int recipe_id;

    @NotNull
    private Date date_time;

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }
}
