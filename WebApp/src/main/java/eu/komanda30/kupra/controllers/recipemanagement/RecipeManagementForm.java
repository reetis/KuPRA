package eu.komanda30.kupra.controllers.recipemanagement;

import eu.komanda30.kupra.entity.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

/**
 * Created by Ignas on 10/23/2014.
 */
public class RecipeManagementForm {
    private String tmpId;

    @Size(min=1, max=256)
    private String name;

    @NotNull
    private int cookingTime;

    @NotNull
    private boolean publicAccess;

    @Size(min=1, max=256)
    private String description;

    @NotNull
    @Size(min=1, max=512)
    private String processDescription;

    @NotNull
    @Min(1)
    private int servings = 1;

    private ArrayList<RecipeProductListUnit> recipeProductListUnits = new ArrayList<RecipeProductListUnit>();

    private Iterable<Product> productsList;

    public void addRecipeProductListUnit(RecipeProductListUnit recipeProductListUnit){
        recipeProductListUnits.add(recipeProductListUnit);
    }
    public ArrayList<RecipeProductListUnit> getRecipeProductListUnits() {
        return recipeProductListUnits;
    }

    public void setRecipeProductListUnits(ArrayList<RecipeProductListUnit> recipeProductListUnits) {
        this.recipeProductListUnits = recipeProductListUnits;
    }

    public Iterable<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(Iterable<Product> productsList) {
        this.productsList = productsList;
    }

    public String getTmpId() {
        return tmpId;
    }

    public void setTmpId(String tmpId) {
        this.tmpId = tmpId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public boolean isPublicAccess() {
        return publicAccess;
    }

    public void setPublicAccess(boolean publicAccess) {
        this.publicAccess = publicAccess;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    public void setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

}
