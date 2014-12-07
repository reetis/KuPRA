package eu.komanda30.kupra.controllers.reciperead;

/**
 * Created by Lukas on 2014.12.07.
 */
public class CommentUnit {
    private String author;
    private String comment;
    private String commentAuthorId;

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
}
