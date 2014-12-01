package eu.komanda30.kupra.uploads;

public class UploadedImageInfo {
    private final String imgId;
    private final String imageUrl;
    private final String thumbUrl;

    public UploadedImageInfo(String imgId, String imageUrl, String thumbUrl) {
        this.imgId = imgId;
        this.imageUrl = imageUrl;
        this.thumbUrl = thumbUrl;
    }

    public String getImgId() {
        return imgId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }
}
