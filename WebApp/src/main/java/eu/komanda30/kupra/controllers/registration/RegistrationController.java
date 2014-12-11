package eu.komanda30.kupra.controllers.registration;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UserProfile;
import eu.komanda30.kupra.repositories.KupraUsers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private RegistrationFormValidator registrationFormValidator;

    @Resource
    private PasswordEncoder encoder;

    @RequestMapping(method = RequestMethod.GET)
    public String showForm(final RegistrationForm form) {
        return "registration";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(registrationFormValidator);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid final RegistrationForm form,
                         final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        final String userId = form.getUsername();
        final UserProfile userProfile = new UserProfile();
        userProfile.setName(form.getName());
        userProfile.setSurname(form.getSurname());
        userProfile.setEmail(form.getEmail());

        final KupraUser user = new KupraUser(userId, userProfile);
        user.setLoginDetails(form.getUsername(), form.getPassword(), encoder);

        kupraUsers.save(user);
        return "redirect:/login";
    }
}
