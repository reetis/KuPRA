package eu.komanda30.kupra.controllers.login;

import javax.validation.constraints.Size;

public class LoginForm {

    @Size(min=1,max = 25)
    private String username;

    @Size(min=1,max = 25)
    private String password;

    private boolean rememberMe;

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

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
