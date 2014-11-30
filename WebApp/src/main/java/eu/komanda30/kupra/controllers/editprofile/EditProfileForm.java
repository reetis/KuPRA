package eu.komanda30.kupra.controllers.editprofile;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Lukas on 2014.10.23.
 */
public class EditProfileForm {

    @NotNull
    @Size(min=3, max=64)
    private String name;

    @NotNull
    @Size(min=3, max=64)
    private String surname;

    @NotNull
    @Size(min=3, max=64)
    @Email
    private String email;

   /* @NotNull*/
    @Size(min=0, max=256)
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
