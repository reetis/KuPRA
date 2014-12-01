package eu.komanda30.kupra.controllers.editprofile;

import eu.komanda30.kupra.uploads.UploadedImageInfo;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class ProfilePhotoList {
    private UploadedImageInfo mainPhoto;

    public UploadedImageInfo getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(UploadedImageInfo mainPhoto) {
        this.mainPhoto = mainPhoto;
    }
}
