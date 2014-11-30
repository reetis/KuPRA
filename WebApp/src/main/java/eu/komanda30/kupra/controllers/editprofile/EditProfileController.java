package eu.komanda30.kupra.controllers.editprofile;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.repositories.KupraUsers;
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
    private KupraUsers kupraUsers;

    @Resource
    private EditProfileValidator editProfileValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(editProfileValidator);
    }

    @RequestMapping(value="/edit_profile", method = RequestMethod.GET)
    public String showForm(final EditProfileForm form, final EditPasswordForm passForm) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser user = kupraUsers.findOne(UserId.forUsername(auth.getName()));
        form.setName(user.getName());
        form.setSurname(user.getSurname());
        form.setEmail(user.getEmail());
        form.setDescription(user.getDescription());
        return "editProfile";
    }

    @RequestMapping(value="/edit_profile", method = RequestMethod.POST)
    public String submit(@Valid final EditProfileForm form,
                         final BindingResult bindingResult,
                         @Valid final EditPasswordForm passForm,
                         final BindingResult passErrors) {
        if (bindingResult.hasErrors()) {
            return "editProfile";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

       if (!passForm.getPassword().isEmpty()) {
           if (passErrors.hasErrors()) {
               return "editProfile";
           } else {
               userRegistrar.editPassword(UserId.forUsername(auth.getName()), passForm.getNewPassword());
           }
       }

        userRegistrar.editProfile(UserId.forUsername(auth.getName()), form.getName(), form.getSurname(),
                form.getEmail(), form.getDescription());

/*        if (bindingResult.hasErrors()) {
            return "editProfile";
        }*/

       /* Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userRegistrar.editProfile(UserId.forUsername(auth.getName()), form.getNewPassword(), form.getName(),
                                form.getSurname(), form.getEmail(), form.getDescription());*/
        return "redirect:/";

    }


}
