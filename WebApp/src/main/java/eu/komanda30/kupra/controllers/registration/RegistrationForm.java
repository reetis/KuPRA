package eu.komanda30.kupra.controllers.registration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class RegistrationForm {
    @NotNull
    @Size(min=3, max=64)
    private String name;

    @NotNull
    @Size(min=3, max=64)
    private String surname;

    @NotNull
    @Size(min=3, max=64)
    private String username;

    @NotNull
    @Size(min=8, max=64)
    private String password;

    @NotNull
    @Size(min=8, max=64)
    @Length
    private String passwordRepeat;

    @NotNull
    @Size(min=1, max=64)
    @Email
    private String email;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}
