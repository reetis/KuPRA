package eu.komanda30.kupra.controllers.friendship.friends_list;

import java.util.ArrayList;

/**
 * Created by Ignas on 11/27/2014.
 */
public class FriendListForm {
    private ArrayList<FriendListUnit> friendListUnits = new ArrayList<FriendListUnit>();

    public ArrayList<FriendListUnit> getFriendListUnits() {
        return friendListUnits;
    }

    public void setFriendListUnits(ArrayList<FriendListUnit> friendListUnits) {
        this.friendListUnits = friendListUnits;
    }

    public void addFriendListUnit(FriendListUnit friendListUnit){
        friendListUnits.add(friendListUnit);
    }
}
