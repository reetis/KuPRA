package eu.komanda30.kupra.controllers.recipelist;

/**
* Created by Rytis on 2014-11-27.
*/
public class RecipePreview {
    private String name;

    private boolean publicAccess;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
