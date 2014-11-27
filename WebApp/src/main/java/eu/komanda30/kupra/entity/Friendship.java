package eu.komanda30.kupra.entity;

import javax.persistence.*;


@Table(name="friendship")
@Entity
@SequenceGenerator(
        name="friendshipIdSequence",
        sequenceName="friendship_seq",
        allocationSize=1)
public class Friendship {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="friendshipIdSequence")
    private int friendship_id;

    private String source_id;

    private String target_id;

    public int getFriendship_id() {
        return friendship_id;
    }

    public void setFriendship_id(int friendship_id) {
        this.friendship_id = friendship_id;
    }

    public String getSourche_id() {
        return source_id;
    }

    public void setSourche_id(String sourche_id) {
        this.source_id = sourche_id;
    }

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }
}
