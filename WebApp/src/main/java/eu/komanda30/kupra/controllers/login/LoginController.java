package eu.komanda30.kupra.controllers.login;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Gintare on 2014-10-23.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String showLogin(final LoginForm form,
                            final BindingResult bindingResult,
                            @RequestParam(value = "error", required = false) final String error) {
        LOG.debug("Login requested for user: {}", form.getUsername());

        //Response from spring security
        if (error != null) {
            bindingResult.reject("authFailed");
        }
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String login(@Valid final LoginForm form,
                         final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        if (form.isRememberMe()) {
            return "forward:/login/process?remember-me=1";
        } else {
            return "forward:/login/process";
        }
    }
}
