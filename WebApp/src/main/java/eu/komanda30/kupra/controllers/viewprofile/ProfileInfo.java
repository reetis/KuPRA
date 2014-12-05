package eu.komanda30.kupra.controllers.viewprofile;

import eu.komanda30.kupra.uploads.UploadedImageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rytis on 2014-12-02.
 */

public class ProfileInfo {
    private String userId;
    private String name;
    private String surname;
    private String email;
    private String description;
    private boolean friend;
    private UploadedImageInfo photo;
    private List<RecipePreview> recipes = new ArrayList<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public UploadedImageInfo getPhoto() {
        return photo;
    }

    public void setPhoto(UploadedImageInfo photo) {
        this.photo = photo;
    }

    public List<RecipePreview> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipePreview> recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(RecipePreview recipe) {
        this.recipes.add(recipe);
    }
}
