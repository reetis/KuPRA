package eu.komanda30.kupra.controllers.resetpassword.email;

import eu.komanda30.kupra.entity.KupraUser;

import java.util.Locale;

public interface ResetPasswordEmailSender {
    void sendResetPasswordEmail(String ipAddress, KupraUser user, String email, Locale locale,
                                String token);
}
