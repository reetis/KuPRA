package eu.komanda30.kupra.entity;

import javax.persistence.*;

/**
 * Created by Ignas on 10/23/2014.
 */
@Table(name="recipe")
@Entity
@SequenceGenerator(
        name="recipeIdSequence",
        sequenceName="recipe_seq",
        allocationSize=1
)
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="recipeIdSequence")
    private Integer recipe_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int cookingTime;

    @Column(nullable = false)
    private boolean publicAccess;

    private String description;

    @Column(nullable = false)
    private String processDescription;

    public Integer getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Integer recipe_id) {
        this.recipe_id = recipe_id;
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
}
