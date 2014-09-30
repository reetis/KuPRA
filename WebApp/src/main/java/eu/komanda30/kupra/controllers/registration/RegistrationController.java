package eu.komanda30.kupra.controllers.registration;

import eu.komanda30.kupra.entity.User;
import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.services.PasswordHasher;
import eu.komanda30.kupra.services.UserRegistrar;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Resource
    private UserRegistrar userRegistrar;

    @Resource
    private PasswordHasher passwordHasher;

    @Resource
    private RegistrationFormValidator registrationFormValidator;

    @RequestMapping(method = RequestMethod.GET)
    public String showForm(final RegistrationForm form) {
        return "registration";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(registrationFormValidator);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid final RegistrationForm form,
                         final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        final User user = new User(new UserId(form.getLogin()));
        user.setName(form.getName());
        user.setSurname(form.getSurname());
        user.setEmail(form.getEmail());
        user.setPasswordHash(passwordHasher.hash(form.getPassword()));
        userRegistrar.registerUser(user);

        return "registration";
    }

    /*@ModelAttribute("allCountries")
    public String[] populateCountries() {
        return new String[] {
                "Lithuania",
                "Russia",
                "Latvia"
        };
    } */
}
