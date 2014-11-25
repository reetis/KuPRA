package eu.komanda30.kupra.controllers.recipemanagement;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ignas on 10/23/2014.
 */
public class RecipeManagementForm {

    @NotBlank
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
}
