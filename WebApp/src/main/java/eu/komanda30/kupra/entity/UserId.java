package eu.komanda30.kupra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserId implements Serializable {
    @Column(length = 24)
    private String userId;

    //For hibernate
    protected UserId() {}

    public static UserId forUsername(String username) {
        return new UserId(username);
    }

    protected UserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || o instanceof UserId && userId.equals(((UserId) o).userId);

    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserId{");
        sb.append("username='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
