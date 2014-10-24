package eu.komanda30.kupra.controllers.login;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Gintare on 2014-10-23.
 */
public class LoginForm {

    @NotNull
    @Size(min=3, max=64)
    private String username;

    @NotNull
    @Size(min=3, max=64)
    private String password;

    private boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
