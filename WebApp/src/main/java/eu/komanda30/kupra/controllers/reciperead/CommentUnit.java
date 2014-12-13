package eu.komanda30.kupra.controllers.reciperead;

import eu.komanda30.kupra.entity.UserProfileImage;

import java.util.Date;

public class CommentUnit {
    private String author;
    private String comment;
    private String commentAuthorId;
    private Date date;
    private UserProfileImage image;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentAuthorId() {
        return commentAuthorId;
    }

    public void setCommentAuthorId(String commentAuthorId) {
        this.commentAuthorId = commentAuthorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserProfileImage getImage() {
        return image;
    }

    public void setImage(UserProfileImage image) {
        this.image = image;
    }
}
