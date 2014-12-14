package eu.komanda30.kupra.entity;

import javax.persistence.*;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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


    @Column(nullable = false)
    private int recipe_id;

    @Column(nullable = false)
    private Date date_time;

    @Column(nullable = false)
    private boolean completed;

    @Column
    private int score;

    @Column
    private int servings;

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
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
}

