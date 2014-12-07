package eu.komanda30.kupra.controllers.friendship.friends_list;

import eu.komanda30.kupra.entity.Friendship;

/**
 * Created by Ignas on 11/27/2014.
 */
public class FriendListUnit {

    private String name;

    private String surname;

    private Friendship friendship;

    private String userId;

    private String sourceId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Friendship getFriendship() {
        return friendship;
    }

    public void setFriendship(Friendship friendship) {
        this.friendship = friendship;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
