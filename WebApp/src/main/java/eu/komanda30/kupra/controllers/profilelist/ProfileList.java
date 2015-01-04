package eu.komanda30.kupra.controllers.profilelist;

import java.util.ArrayList;
import java.util.List;

public class ProfileList {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public void sort(){
        users.sort((x, y) -> x.getDisplayName().compareToIgnoreCase(y.getDisplayName()));
    }

    public static class User {
        private String userId;
        private String displayName;
        private String imageUrl;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
