package eu.komanda30.kupra.controllers.recipemanagement;

import java.util.ArrayList;
import java.util.List;

public class RecipeImagesForm {
    public static class ImageInfo {
        private final String imageUrl;
        private final String thumbUrl;

        public ImageInfo(String imageUrl, String thumbUrl) {
            this.imageUrl = imageUrl;
            this.thumbUrl = thumbUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getThumbUrl() {
            return thumbUrl;
        }
    }

    private List<ImageInfo> images = new ArrayList<>();

    public Iterable<ImageInfo> getImages() {
        return images;
    }

    public void addImage(String imageUrl, String thumbUrl) {
        images.add(new ImageInfo(imageUrl, thumbUrl));
    }
}
