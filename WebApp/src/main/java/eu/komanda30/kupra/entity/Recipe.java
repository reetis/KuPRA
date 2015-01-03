package eu.komanda30.kupra.entity;

import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Table(name = "recipe")
@Entity
@SequenceGenerator(
        name = "recipeIdSequence",
        sequenceName = "recipe_seq",
        allocationSize = 1
)
@Indexed
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipeIdSequence")
    private Integer recipeId;

    @Column(nullable = false)
    @Field
    @Boost(1.1f)
    private String name;

    @Column(nullable = false)
    private int cookingTime;

    @Column(nullable = false)
    @Field
    private boolean publicAccess = false;

    @Field
    @Boost(1.0f)
    private String description;

    @Column(nullable = false)
    @Field
    @Boost(0.9f)
    private String processDescription;

    @Column(nullable = false)
    private int servings;

    @Column(nullable = false)
    private Date recipeDate;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeProduct> recipeProductList;

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    @IndexedEmbedded(includePaths = {"username", "profile.fullName"})
    @Boost(0.6f)
    private KupraUser author;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipe_id", nullable = false)
    private List<RecipeImage> recipeImages;

    @JoinColumn(name = "recipe_id", nullable = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("comment_date DESC")
    @IndexedEmbedded
    @Boost(0.2f)
    private List<Comment> recipeComments;

    @Field
    @Boost(0.3f)
    public String getProductNames() {
        return recipeProductList.parallelStream()
                .map(it -> it.getProduct().getName())
                .collect(Collectors.joining(" "));
    }

    public List<RecipeProduct> getRecipeProductList() {
        return recipeProductList;
    }

    public void setRecipeProductList(List<RecipeProduct> recipeProductList) {
        this.recipeProductList = recipeProductList;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
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

    public void addProduct(RecipeProduct product) {
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

    public Date getRecipeDate() {
        return recipeDate;
    }

    public void setRecipeDate(Date recipe_date) {
        this.recipeDate = recipe_date;
    }

    public Optional<RecipeImage> getMainRecipeImage() {
        return recipeImages.stream().findFirst();
    }

    public ArrayList<RecipeProduct> getProductsNeeded(BigDecimal servings) {
        ArrayList<RecipeProduct> productsNeeded = new ArrayList<>();

        BigDecimal recipeServings = new BigDecimal(this.getServings());
        BigDecimal menuServings = servings;

        for (RecipeProduct recipeProduct : this.getRecipeProductList()) {
            BigDecimal amountNeeded = recipeProduct.getQuantity().divide(recipeServings, 2).multiply(menuServings);
            Boolean productSet = false;
            for (RecipeProduct neededProduct : productsNeeded) {
                if (recipeProduct.getProduct().equals(neededProduct.getProduct())) {
                    neededProduct.addQuantity(amountNeeded);
                    productSet = true;
                    break;
                }
            }

            if (!productSet) {
                RecipeProduct newProduct = new RecipeProduct();
                newProduct.setRecipe(this);
                newProduct.setProduct(recipeProduct.getProduct());
                newProduct.setQuantity(amountNeeded);
                productsNeeded.add(newProduct);
            }
        }

        return productsNeeded;
    }
}
