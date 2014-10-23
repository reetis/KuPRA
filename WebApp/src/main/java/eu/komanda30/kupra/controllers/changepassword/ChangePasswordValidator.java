package eu.komanda30.kupra.controllers.changepassword;

import eu.komanda30.kupra.controllers.registration.RegistrationForm;
import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.entity.UsernamePasswordAuth;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.UsernamePasswordAuths;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

/**
 * Created by Lukas on 2014.10.23.
 */

@Component
public class ChangePasswordValidator implements Validator {

    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private UsernamePasswordAuths usernamePasswordAuths;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return ChangePasswordForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final ChangePasswordForm form = (ChangePasswordForm)target;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuth passwordAuth = usernamePasswordAuths.findByUserId(new UserId(auth.getName()));

        String encodedPassword = passwordEncoder.encode(form.getPassword());

        if (!encodedPassword.equals(passwordAuth.getPassword())){
            errors.rejectValue("password", "DoesNotMatch");
        }
        if (!form.getNewPassword().equals(form.getConfirmNewPassword())) {
            errors.rejectValue("confirmNewPassword", "DoesNotMatch");
        }
    }
}
