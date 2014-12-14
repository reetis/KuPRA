package eu.komanda30.kupra.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="password_auth")
public class UsernamePasswordAuth {
    @Id
    private String userId;

    @MapsId
    @OneToOne(mappedBy = "usernamePasswordAuth")
    @JoinColumn(name = "user_id")
    private KupraUser user;

    @Column(length = 24, unique = true)
    private String username;

    @Column(length = 64)
    private String password;

    @Column(length = 64)
    private String resetPasswordToken;

    @Column
    private Date resetPasswordTokenValidTill;

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

    public String generateResetPasswordToken(Date validTill) {
        resetPasswordToken = UUID.randomUUID().toString();
        resetPasswordTokenValidTill = validTill;
        return resetPasswordToken;
    }

    public void invalidateResetPasswordToken() {
        resetPasswordToken = null;
        resetPasswordTokenValidTill = null;
    }
}
