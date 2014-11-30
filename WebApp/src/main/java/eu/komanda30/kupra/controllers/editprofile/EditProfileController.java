package eu.komanda30.kupra.controllers.editprofile;

import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.services.UserRegistrar;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Lukas on 2014.10.23.
 */
@Controller
public class EditProfileController {
    @Resource
    private UserRegistrar userRegistrar;

    @Resource
    private EditProfileValidator editProfileValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(editProfileValidator);
    }

    @RequestMapping(value="/edit_profile", method = RequestMethod.GET)
    public String showForm(final EditProfileForm form) {
        return "editProfile";
    }

    @RequestMapping(value="/edit_profile", method = RequestMethod.POST)
    public String submit(@Valid final EditProfileForm form,
                         final BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "editProfile";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userRegistrar.editProfile(UserId.forUsername(auth.getName()), form.getNewPassword(), form.getName(),
                                form.getSurname(), form.getEmail(), form.getDescription());
        return "editProfile";

    }


}
