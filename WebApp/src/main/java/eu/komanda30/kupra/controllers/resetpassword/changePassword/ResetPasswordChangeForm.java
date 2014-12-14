package eu.komanda30.kupra.controllers.resetpassword.changePassword;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ResetPasswordChangeForm {

    @NotNull
    private String token;

    @NotNull
    @Size(min=8, max=64)
    private String password;

    @NotNull
    @Size(min=8, max=64)
    private String passwordRepeat;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
