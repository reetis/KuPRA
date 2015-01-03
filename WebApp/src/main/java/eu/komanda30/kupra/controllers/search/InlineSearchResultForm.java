package eu.komanda30.kupra.controllers.search;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class InlineSearchResultForm {
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private List<RecipeRow> recipes = new ArrayList<>();
    private List<PersonRow> persons = new ArrayList<>();

    public void addRecipeRow(RecipeRow recipeRow) {
        recipes.add(recipeRow);
    }

    public void addPersonRow(PersonRow personRow) {
        persons.add(personRow);
    }

    public List<RecipeRow> getRecipes() {
        return ImmutableList.copyOf(recipes);
    }

    public List<PersonRow> getPersons() {
        return ImmutableList.copyOf(persons);
    }

    public static class RecipeRow {
        private String imageUrl;
        private String name;
        private String description;
        private int recipeId;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(int recipe_id) {
            this.recipeId = recipe_id;
        }
    }

    public static class PersonRow {
        private String imageUrl;
        private String name;
        private String userId;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String user_id) {
            this.userId = user_id;
        }
    }
}
