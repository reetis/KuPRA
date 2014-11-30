package eu.komanda30.kupra;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

public class SessionTempImages {
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

    private Map<String, List<File>> images = new HashMap<>();

    public void addImageToRecipe(String tmpId, File image) {
        List<File> files = images.get(tmpId);
        if (files == null) {
            files = new ArrayList<>();
            images.put(tmpId, files);
        }

        files.add(image);
    }

    public Iterable<File> getImages(String tmpId) {
        return ImmutableList.copyOf(images.getOrDefault(tmpId, ImmutableList.of()));
    }
}
