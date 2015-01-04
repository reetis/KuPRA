package eu.komanda30.kupra.controllers.viewprofile;

import eu.komanda30.kupra.entity.UserProfileImage;

import java.util.ArrayList;
import java.util.List;

public class ProfileInfo {

    private String userId;
    private String name;
    private String surname;
    private String email;
    private String description;
    private String displayName;
    private boolean friend;
    private boolean requestSent;
    private boolean requestReceived;
    private boolean personal;
    private UserProfileImage photo;

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

    public List<RecipePreview> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipePreview> recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(RecipePreview recipe) {
        this.recipes.add(recipe);
    }

    public UserProfileImage getPhoto() {
        return photo;
    }

    public void setPhoto(UserProfileImage photo) {
        this.photo = photo;
    }

    public boolean isRequestSent() {
        return requestSent;
    }

    public void setRequestSent(boolean requestSent) {
        this.requestSent = requestSent;
    }

    public boolean isRequestReceived() {
        return requestReceived;
    }

    public void setRequestReceived(boolean requestReceived) {
        this.requestReceived = requestReceived;
    }

    public boolean isPersonal() {
        return personal;
    }

    public void setPersonal(boolean personal) {
        this.personal = personal;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
