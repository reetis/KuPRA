package eu.komanda30.kupra.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="user_profile_image")
@SequenceGenerator(
        name="userProfileImgIdSequence",
        sequenceName="user_profile_image_seq",
        allocationSize=1
)
public class UserProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userProfileImgIdSequence")
    private Integer id;

    private String imageUrl;
    private String thumbUrl;

    //For hibernate
    protected UserProfileImage() {
    }

    public UserProfileImage(String imageUrl, String thumbUrl) {
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
