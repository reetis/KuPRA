package eu.komanda30.kupra.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Locale;

@Embeddable
public class UserProfile {
    @Column(length = 64)
    private String name;

    @Column(length = 64)
    private String surname;

    @Column(unique = true, length = 64)
    private String email;

    @Column(length = 64)
    private String description;

    @Column(length = 64)
    private Locale locale;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
