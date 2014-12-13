package eu.komanda30.kupra.controllers.resetpassword.changePassword;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.repositories.KupraUsers;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/resetPassword/change")
public class ResetPasswordChangeController {

    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public String getPasswordChangeForm(
            @RequestParam("token") String token,
            ResetPasswordChangeForm form) {
        final KupraUser user = kupraUsers.findByPasswordResetToken(token);
        if (user == null) {
            return "resetPasswordTokenInvalid";
        }

        return "resetPasswordChangeForm";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String submit(
            @RequestParam("token") String token,
            @Valid ResetPasswordChangeForm form,
            BindingResult bindingResult) {
        final KupraUser user = kupraUsers.findByPasswordResetToken(token);
        if (user == null) {
            return "resetPassword/token-invalid";
        }
        if (bindingResult.hasErrors()) {
            return "resetPassword/change-form";
        }

        user.setLoginDetails(user.getUsernamePasswordAuth().getUsername(), form.getPassword(), passwordEncoder);
        kupraUsers.save(user);
        return "resetPassword/change-success";
    }
}
