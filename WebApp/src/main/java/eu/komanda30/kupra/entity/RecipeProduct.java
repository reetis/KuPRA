package eu.komanda30.kupra.entity;

import javax.persistence.*;
import java.math.BigDecimal;

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

    private BigDecimal quantity;

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(BigDecimal quantity) {
        this.quantity = this.quantity.add(quantity);
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
