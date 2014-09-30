package eu.komanda30.kupra.services.impl;

import eu.komanda30.kupra.entity.User;
import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.repositories.Users;
import eu.komanda30.kupra.services.UserRegistrar;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class UserRegistrarImpl implements UserRegistrar {

    @Resource
    private Users users;

    @Override
    public boolean isLoginUsed(String login) {
        return users.findByUserId(new UserId(login)) != null;
    }

    @Override
    public boolean isEmailUsed(String email) {
        return users.findByEmail(email) != null;
    }

    @Override
    @Transactional
    public void registerUser(User user) {
        users.save(user);
    }
}
