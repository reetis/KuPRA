package eu.komanda30.kupra.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeProduct> recipeProductList;

    @AttributeOverrides( {
            @AttributeOverride(name="userId", column = @Column(name="author") ),
    } )
   
    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private KupraUser author;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="recipe_id", nullable = false)
    private List<RecipeImage> recipeImages;

    @JoinColumn(name = "recipe_id", nullable = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> recipeComments;

    public List<RecipeProduct> getRecipeProductList() {
        return recipeProductList;
    }

    public void setRecipeProductList(List<RecipeProduct> recipeProductList) {
        this.recipeProductList = recipeProductList;
    }

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

    public KupraUser getAuthor() {
        return author;
    }

    public void setAuthor(KupraUser author) {
        this.author = author;
    }

    public void addImage(String imgUrl, String thumbUrl) {
        if (recipeImages == null) {
            recipeImages = new ArrayList<>();
        }
        recipeImages.add(new RecipeImage(imgUrl, thumbUrl));
    }

    public void addProduct(RecipeProduct product){
        recipeProductList.add(product);
    }

    public List<Comment> getRecipeComments() {
        return recipeComments;
    }

    public void setRecipeComments(List<Comment> recipeComments) {
        this.recipeComments = recipeComments;
    }

    public void addRecipeComments(Comment comment) {
        if (recipeComments == null) {
            recipeComments = new ArrayList<>();
        }
        recipeComments.add(comment);
    }

    public List<RecipeImage> getRecipeImages() {
        return recipeImages;
    }
}
