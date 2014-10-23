package eu.komanda30.kupra.controllers.login;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Gintare on 2014-10-23.
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    public String login(@Valid final LoginForm form,
                         final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "connectToSystem";
        }
        return "";
    }
}
