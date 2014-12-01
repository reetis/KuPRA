package eu.komanda30.kupra.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="`user`")
@Entity
public class KupraUser {
    @Id
    private UserId userId;

    @Embedded
    private UserProfile userProfile;

    private boolean isAdmin;

    //for hibernate
    protected KupraUser() {

    }

    public KupraUser(UserId userId, UserProfile profile) {
        this.userId = userId;
        this.userProfile = profile;
        this.isAdmin = false;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public UserId getUserId() {
        return userId;
    }

}
