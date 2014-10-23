package eu.komanda30.kupra.controllers.login;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Gintare on 2014-10-23.
 */
public class LoginForm {

    @NotNull
    @Size(min=3, max=64)
    private String name;

    @NotNull
    @Size(min=3, max=64)
    private String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
