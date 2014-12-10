package eu.komanda30.kupra.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Ignas on 12/3/2014.
 */
@Entity
@Table(name="recipe_product")
@SequenceGenerator(
        name="recipeProductIdSequence",
        sequenceName="recipe_product_seq",
        allocationSize=1
)
public class RecipeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipeProductIdSequence")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private Double quantity;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
