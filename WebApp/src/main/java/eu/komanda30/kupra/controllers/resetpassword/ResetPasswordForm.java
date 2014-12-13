package eu.komanda30.kupra.controllers.resetpassword;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class ResetPasswordForm {
    @Size(min = 1, max = 64)
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
