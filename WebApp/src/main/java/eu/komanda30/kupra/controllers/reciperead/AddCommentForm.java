package eu.komanda30.kupra.controllers.reciperead;

import eu.komanda30.kupra.entity.KupraUser;

import javax.validation.constraints.Size;

/**
 * Created by Lukas on 2014.12.05.
 */
public class AddCommentForm {

    @Size(min = 0, max = 256)
    private String comment;

    private KupraUser author;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public KupraUser getAuthor() {
        return author;
    }

    public void setAuthor(KupraUser author) {
        this.author = author;
    }
}
