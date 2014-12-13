package eu.komanda30.kupra.controllers.resetpassword;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UsernamePasswordAuth;
import eu.komanda30.kupra.repositories.KupraUsers;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/resetPassword")
public class ResetPasswordController {

    private static final Logger LOG = LoggerFactory.getLogger(ResetPasswordController.class);

    @Resource
    private ResetPasswordFormValidator resetPasswordFormValidator;

    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private JavaMailSender mailSender;

    @Value("${email.from}")
    private String emailFrom;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(resetPasswordFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String get(@ModelAttribute("form") final ResetPasswordForm form) {
        return "resetPassword";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("form") final ResetPasswordForm form,
                        final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "resetPassword";
        }

        LOG.debug("Reset password request received for email: " + form.getEmail());
        resetPasswordForEmail(form.getEmail());

        return "resetPasswordSuccess";
    }

    private void resetPasswordForEmail(String email) {
        final KupraUser user = kupraUsers.findByEmail(email);
        final UsernamePasswordAuth auth = user.getUsernamePasswordAuth();
        final String token = auth.generateResetPasswordToken();

        try {
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom(emailFrom);
            message.setTo(email);
            message.setSubject("Password reset");
            message.setText("Password reset token: " + token);
            this.mailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            LOG.error("Failed to send password reset email", e);
        }
    }
}
