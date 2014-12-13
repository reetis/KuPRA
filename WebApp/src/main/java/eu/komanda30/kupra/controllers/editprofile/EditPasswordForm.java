package eu.komanda30.kupra.controllers.editprofile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditPasswordForm {


    @NotNull
    @Size(min=8, max=64)
    private String password;

    @NotNull
    @Size(min=8, max=64)
    private String newPassword;

    @NotNull
    @Size(min=8, max=64)
    private String confirmNewPassword;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

}
