package eu.komanda30.kupra.controllers.reciperead;

import eu.komanda30.kupra.entity.KupraUser;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ignas on 12/2/2014.
 */
public class RecipeReadForm {
    private String name;

    private int cookingTime;

    private boolean publicAccess;

    private String description;

    private String processDescription;

    private int servings;

    private KupraUser kupraUser;

    private ArrayList<CommentUnit> comments = new ArrayList<CommentUnit>();

    private String recipeAuthor;

    private String recipeAuthorId;

    private Date date;

    public KupraUser getKupraUser() {
        return kupraUser;
    }

    public void setKupraUser(KupraUser kupraUser) {
        this.kupraUser = kupraUser;
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

    public ArrayList<CommentUnit> getComments() {
        return comments;
    }

    public void addComment(CommentUnit comment) {
        comments.add(comment);
    }


    public String getRecipeAuthor() {
        return recipeAuthor;
    }

    public void setRecipeAuthor(String recipeAuthor) {
        this.recipeAuthor = recipeAuthor;
    }

    public void setRecipeAuthorId(String recipeAuthorId) {
        this.recipeAuthorId = recipeAuthorId;
    }

    public String getRecipeAuthorId() {
        return recipeAuthorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}