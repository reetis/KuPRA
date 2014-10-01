package eu.komanda30.kupra.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserId implements Serializable {
    private String login;

    //For hibernate
    protected UserId() {}

    public UserId(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || o instanceof UserId && login.equals(((UserId) o).login);

    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
