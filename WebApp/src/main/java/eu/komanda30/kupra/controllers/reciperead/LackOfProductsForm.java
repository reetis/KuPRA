package eu.komanda30.kupra.controllers.reciperead;

import java.util.ArrayList;

/**
 * Created by Ignas on 12/17/2014.
 */
public class LackOfProductsForm {

    private String recipeName;
    private ArrayList<LackOfProductsItem> lackOfProductsItems = new ArrayList<LackOfProductsItem>();

    public ArrayList<LackOfProductsItem> getLackOfProductsItems() {
        return lackOfProductsItems;
    }

    public void setLackOfProductsItems(ArrayList<LackOfProductsItem> lackOfProductsItems) {
        this.lackOfProductsItems = lackOfProductsItems;
    }

    public void addLackOfProductsItem(LackOfProductsItem lackOfProductsItem){
        this.lackOfProductsItems.add(lackOfProductsItem);
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
