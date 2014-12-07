package eu.komanda30.kupra.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Lukas on 2014.12.05.
 */
@Table(name = "comment")
@Entity
@SequenceGenerator(
        name = "commentIdSequence",
        sequenceName = "comment_seq",
        allocationSize = 1
)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentIdSequence")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private KupraUser author;

    @Column(nullable = false)
    private String comment;


    @Column(nullable = false)
    private Date comment_date;

    public Comment(String comment, KupraUser author, Date date) {
        this.comment = comment;
        this.author = author;
        this.comment_date = date;
    }

    protected Comment() {
    }

    public Integer getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }


    public KupraUser getAuthor() {
        return author;
    }

    public Date getDate() {
        return comment_date;
    }

}
