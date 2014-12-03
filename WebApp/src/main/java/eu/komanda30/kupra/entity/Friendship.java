package eu.komanda30.kupra.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Table(name="friendship")
@Entity
@SequenceGenerator(
        name="friendshipIdSequence",
        sequenceName="friendship_seq",
        allocationSize=1)
public class Friendship {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="friendshipIdSequence")
    private int friendshipId;

    @ManyToOne
    @JoinColumn(name="source_id")
    private KupraUser source;

    @ManyToOne
    @JoinColumn(name="target_id")
    private KupraUser target;

    public KupraUser getFriendOf(String userId){
        if (source.getUserId().equals(userId)){
            return target;
        }else{
            return source;
        }
    }

    public boolean isFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(boolean friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    private boolean friendshipStatus;

    public int getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(int friendshipId) {
        this.friendshipId = friendshipId;
    }

    public KupraUser getSource() {
        return source;
    }

    public void setSource(KupraUser source) {
        this.source = source;
    }

    public KupraUser getTarget() {
        return target;
    }

    public void setTarget(KupraUser target) {
        this.target = target;
    }
}
