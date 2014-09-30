package eu.komanda30.kupra.services;

import eu.komanda30.kupra.entity.User;

public interface UserRegistrar {
    boolean isLoginUsed(String login);
    boolean isEmailUsed(String email);
    void registerUser(User user);
}
