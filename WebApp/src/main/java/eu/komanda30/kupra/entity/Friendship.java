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

    private int sourche_id;

    private int target_id;

    public int getFriendship_id() {
        return friendship_id;
    }

    public void setFriendship_id(int friendship_id) {
        this.friendship_id = friendship_id;
    }

    public int getSourche_id() {
        return sourche_id;
    }

    public void setSourche_id(int sourche_id) {
        this.sourche_id = sourche_id;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }
}
