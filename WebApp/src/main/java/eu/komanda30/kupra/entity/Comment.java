package eu.komanda30.kupra.entity;

import javax.persistence.*;

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


    @Column(nullable = false)
    private String comment;

    public Comment(String comment) {
        this.comment = comment;
    }

    protected Comment() {
    }

    public Integer getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }


}
