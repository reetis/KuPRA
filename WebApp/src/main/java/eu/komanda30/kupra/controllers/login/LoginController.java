package eu.komanda30.kupra.controllers.login;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Gintare on 2014-10-23.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String showLogin(final LoginForm form) {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String login(@Valid final LoginForm form,
                         final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        return "forward:/login_process";
    }
}
