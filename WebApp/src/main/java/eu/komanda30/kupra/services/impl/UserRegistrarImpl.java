package eu.komanda30.kupra.services.impl;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.entity.UserProfile;
import eu.komanda30.kupra.entity.UsernamePasswordAuth;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.UsernamePasswordAuths;
import eu.komanda30.kupra.services.UserRegistrar;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrarImpl implements UserRegistrar {

    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private UsernamePasswordAuths usernamePasswordAuths;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void registerUser(UserId userId, UserProfile userProfile, String username,
                             String password) {
        final String encodedPassword = passwordEncoder.encode(password);
        final KupraUser kupraUser = new KupraUser(userId, userProfile);
        kupraUsers.save(kupraUser);

        final UsernamePasswordAuth usernamePasswordAuth = new UsernamePasswordAuth(
                username, encodedPassword, userId);
        usernamePasswordAuths.save(usernamePasswordAuth);
    }

    @Transactional
    @Override
    public void changePassword(UserId userId, String password) {
        final UsernamePasswordAuth passwordAuth = usernamePasswordAuths.findByUserId(userId);
        passwordAuth.setPassword(password);
    }
}
