package eu.komanda30.kupra.controllers.resetpassword;

import eu.komanda30.kupra.controllers.resetpassword.email.ResetPasswordEmailSender;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.repositories.KupraUsers;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/resetPassword")
public class ResetPasswordController {

    private static final Logger LOG = LoggerFactory.getLogger(ResetPasswordController.class);

    @Resource
    private ResetPasswordFormValidator resetPasswordFormValidator;

    @Resource
    private ResetPasswordEmailSender resetPasswordEmailSender;

    @Resource
    private KupraUsers kupraUsers;

    @Value("${resetPassword.tokenValidMinutes}")
    private int tokenValidMinutes;

    @InitBinder("resetPasswordForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(resetPasswordFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String get(final ResetPasswordForm form) {
        return "resetPassword/reset";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid final ResetPasswordForm form,
                        final BindingResult bindingResult,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "resetPassword/reset";
        }

        LOG.debug("Reset password request received for email: " + form.getEmail());
        final KupraUser user = kupraUsers.findByEmail(form.getEmail());


        final Date tokenValidDate = DateUtils.addMinutes(new Date(), tokenValidMinutes);
        final String token = user
                .getUsernamePasswordAuth()
                .generateResetPasswordToken(tokenValidDate);
        kupraUsers.save(user);

        resetPasswordEmailSender.sendResetPasswordEmail(
                request.getRemoteAddr(),
                user, form.getEmail(),  LocaleContextHolder.getLocale(),
                token);

        return "resetPassword/reset-success";
    }
}
