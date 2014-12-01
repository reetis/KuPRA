package eu.komanda30.kupra.controllers.friendsrecipelist;

/**
 * Created by Lukas on 2014.11.30.
 */
public class FriendsRecipePreview {

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

}
