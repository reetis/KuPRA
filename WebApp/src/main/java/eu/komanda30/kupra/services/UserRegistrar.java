package eu.komanda30.kupra.services;

import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.entity.UserProfile;

public interface UserRegistrar {
    void registerUser(UserId userId, UserProfile userProfile, String username, String password);
    void editProfile(UserId userId, String name, String surname, String email,
                    String description);
    void editPassword(UserId userId, String password);
}
