package eu.komanda30.kupra.entity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.List;

@Table(name="`user`")
@Entity
public class KupraUser {
    @Id
    private String userId;

    @Embedded
    private UserProfile userProfile;

    private boolean isAdmin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fridge> fridges;

    @OneToMany(mappedBy = "author")
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "source")
    private List<Friendship> friendships;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UsernamePasswordAuth usernamePasswordAuth;

    //for hibernate
    protected KupraUser() {

    }

    public KupraUser(String userId, UserProfile profile) {
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

    public String getUserId() {
        return userId;
    }

    public UsernamePasswordAuth getUsernamePasswordAuth() {
        return usernamePasswordAuth;
    }

    public void setUsernamePasswordAuth(UsernamePasswordAuth usernamePasswordAuth) {
        this.usernamePasswordAuth = usernamePasswordAuth;
    }

    public void setLoginDetails(String username, String password, PasswordEncoder encoder) {
        final String encodedNewPassword = encoder.encode(password);

        if (usernamePasswordAuth == null) {
            usernamePasswordAuth = new UsernamePasswordAuth(this, username, encodedNewPassword);
        } else {
            setPassword(password, encoder);
        }
    }

    public void setPassword(String password, PasswordEncoder encoder) {
        Assert.notNull(usernamePasswordAuth);
        usernamePasswordAuth.setPassword(encoder.encode(password));
    }

    public List<Fridge> getFridges() {
        return fridges;
    }


    public void setFridges(List<Fridge> fridges) {
        this.fridges = fridges;
    }
}
