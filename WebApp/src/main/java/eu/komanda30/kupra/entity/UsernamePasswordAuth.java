package eu.komanda30.kupra.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="password_auth", uniqueConstraints =
    @UniqueConstraint(columnNames="userId")
)
public class UsernamePasswordAuth {
    @Id
    private String userId;

    @MapsId("userId")
    @OneToOne
    private KupraUser user;

    @Column(length = 24, unique = true)
    private String username;

    @Column(length = 64)
    private String password;

    //for hibernate
    protected UsernamePasswordAuth() {}

    public UsernamePasswordAuth(KupraUser user, String username, String password) {
        this.username = username;
        this.password = password;
        this.user = user;
    }

    public KupraUser getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
