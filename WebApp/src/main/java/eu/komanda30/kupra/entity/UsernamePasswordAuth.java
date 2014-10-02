package eu.komanda30.kupra.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="password_auth", uniqueConstraints =
    @UniqueConstraint(columnNames="userId")
)
public class UsernamePasswordAuth {
    @Id
    @Column(length = 24)
    private String username;

    @Column(length = 64)
    private String password;

    @Embedded
    private UserId userId;

    //for hibernate
    protected UsernamePasswordAuth() {}

    public UsernamePasswordAuth(String username, String password, UserId userId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public UserId getUserId() {
        return userId;
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
