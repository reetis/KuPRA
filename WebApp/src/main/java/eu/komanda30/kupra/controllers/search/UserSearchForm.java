package eu.komanda30.kupra.controllers.search;

import java.util.ArrayList;
import java.util.List;

public class UserSearchForm {
    private String title;
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class User{
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
