package eu.komanda30.kupra.entity;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="recipe_image")
@SequenceGenerator(
        name="recipeImgIdSequence",
        sequenceName="recipe_image_seq",
        allocationSize=1
)
public class RecipeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipeImgIdSequence")
    private Integer id;

    private String imagePath;
    private String thumbPath;

    //For hibernate
    protected RecipeImage() {
    }

    public RecipeImage(File imageFile, File thumbFile) {
        this.imagePath = imageFile.getAbsolutePath();
        this.thumbPath = thumbFile.getAbsolutePath();
    }

    public File getImageFile() {
        return new File(imagePath);
    }

    public File getThumbFile() {
        return new File(thumbPath);
    }
}
