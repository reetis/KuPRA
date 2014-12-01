package eu.komanda30.kupra.controllers.editprofile;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.entity.UsernamePasswordAuth;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.UsernamePasswordAuths;
import eu.komanda30.kupra.services.impl.UserRegistrarImpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Lukas on 2014.10.23.
 */

@Component
public class EditProfileValidator implements Validator {

    public static final Logger LOG = LoggerFactory.getLogger(UserRegistrarImpl.class.getName());

    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private UsernamePasswordAuths usernamePasswordAuths;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public boolean supports(Class<?> clazz) {
        return EditProfileForm.class.isAssignableFrom(clazz)
                || EditPasswordForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof EditProfileForm) {
            validateProfile((EditProfileForm) target, errors);
        } else if (target instanceof EditPasswordForm) {
            validatePassword(target, errors);
        }
    }

    private void validateProfile(EditProfileForm target, Errors errors) {
        final EditProfileForm form = target;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser user = kupraUsers.findOne(UserId.forUsername(auth.getName()));

        if ((kupraUsers.findByEmail(form.getEmail()) != null) && !(form.getEmail().equals(user.getEmail()))) {
            errors.rejectValue("email","AlreadyUsed");
        }
    }

    public void validatePassword(Object target, Errors errors) {
        final EditPasswordForm form = (EditPasswordForm)target;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        UsernamePasswordAuth passwordAuth = usernamePasswordAuths.findByUserId(UserId.forUsername(auth.getName()));


        if(!form.getPassword().equals("")) {
            if (!passwordEncoder.matches(form.getPassword(), passwordAuth.getPassword())) {
                errors.rejectValue("password", "DoesNotMatch");
            }
            if (!form.getNewPassword().equals(form.getConfirmNewPassword())) {
                errors.rejectValue("confirmNewPassword", "DoesNotMatch");
            }
        }
    }
}
