package eu.komanda30.kupra.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
    private boolean publicAccess = false;

    private String description;

    @Column(nullable = false)
    private String processDescription;

    @Column(nullable = false)
    private int servings;

    @AttributeOverrides( {
            @AttributeOverride(name="userId", column = @Column(name="author") ),
    } )
    @Column(name = "author", nullable = false)
    private String author;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="recipe_id", nullable = false)
    private List<RecipeImage> recipeImages;

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

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void addImage(String imgUrl, String thumbUrl) {
        if (recipeImages == null) {
            recipeImages = new ArrayList<>();
        }
        recipeImages.add(new RecipeImage(imgUrl, thumbUrl));
    }
}
