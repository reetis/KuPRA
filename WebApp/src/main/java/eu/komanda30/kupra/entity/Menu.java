package eu.komanda30.kupra.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name="menu")
@Entity
@SequenceGenerator(
        name="menuIdSequence",
        sequenceName="menu_seq",
        allocationSize=1

)
public class Menu {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="menuIdSequence")
    private Integer id;

    @JoinColumn(name="recipe_id")
    @ManyToOne
    private Recipe recipe;

    @Column(nullable = false)
    private Date dateTime;

    @Column(nullable = false)
    private boolean completed;

    @Column
    private int score;

    @Column
    private int servings;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }


}

