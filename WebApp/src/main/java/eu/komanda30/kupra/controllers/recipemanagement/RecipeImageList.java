package eu.komanda30.kupra.controllers.recipemanagement;

import eu.komanda30.kupra.uploads.UploadedImageInfo;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class RecipeImageList {
    private List<UploadedImageInfo> uploadedImages = new ArrayList<>();

    public Iterable<UploadedImageInfo> getUploadedImages() {
        return ImmutableList.copyOf(uploadedImages);
    }

    public void add(UploadedImageInfo image) {
        uploadedImages.add(image);
    }

    public void addAll(Iterable<UploadedImageInfo> images) {
        Iterables.addAll(uploadedImages, images);
    }
}
