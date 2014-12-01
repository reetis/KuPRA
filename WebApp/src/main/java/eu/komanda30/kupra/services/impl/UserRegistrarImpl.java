package eu.komanda30.kupra.services.impl;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.entity.UserProfile;
import eu.komanda30.kupra.entity.UsernamePasswordAuth;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.UsernamePasswordAuths;
import eu.komanda30.kupra.services.UserRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Locale;


@Service
public class UserRegistrarImpl implements UserRegistrar {
    public static final Logger LOG = LoggerFactory.getLogger(UserRegistrarImpl.class.getName());

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
    public void editProfile(UserId userId, String name, String surname, String email,
                            String description){
        final KupraUser user = kupraUsers.findOne(userId);

            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setDescription(description);



        LOG.trace("Atejau i saugojima");
    }

    @Transactional
    @Override
    public void editPassword(UserId userId, String password){
        final UsernamePasswordAuth passwordAuth = usernamePasswordAuths.findByUserId(userId);

        final String encodedNewPassword = passwordEncoder.encode(password);
        passwordAuth.setPassword(encodedNewPassword);

        LOG.trace("Atejau i saugojima");
    }

    @Transactional
    @Override
    public void editLocale(UserId userId, Locale locale){
        final KupraUser user = kupraUsers.findOne(userId);
        user.getUserProfile().setLocale(locale);
    }
}
