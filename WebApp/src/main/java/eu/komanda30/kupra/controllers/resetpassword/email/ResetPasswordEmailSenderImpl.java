package eu.komanda30.kupra.controllers.resetpassword.email;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.repositories.KupraUsers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.google.common.collect.ImmutableMap;

@Service
public class ResetPasswordEmailSenderImpl implements ResetPasswordEmailSender {
    @Resource
    private KupraUsers kupraUsers;

    @Resource(name="mailSender")
    private JavaMailSender mailSender;

    @Value("${email.from}")
    private String emailFrom;

    @Value("${rootUrl}")
    private String rootUrl;

    @Resource
    private TemplateEngine templateEngine;

    @Resource
    private MessageSource messageSource;

    @Override
    public void sendResetPasswordEmail(String ipAddress, KupraUser user, String email,
                                       Locale locale, String token) {
        try {
            final ResetPasswordEmailForm emailForm = buildPasswordResetEmailForm(ipAddress, user,
                    token);
            final String html = buildPasswordResetHtml(emailForm, locale);

            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject(messageSource.getMessage("resetPassword.email.subject",
                    new Object[]{user.getUserId()}, locale));
            message.setFrom(emailFrom);
            message.setTo(email);
            message.setText(html, true);
            this.mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

    private String buildPasswordResetHtml(ResetPasswordEmailForm emailForm, Locale locale) {
        return templateEngine.process("resetPassword/email.html",
                new Context(locale,
                        ImmutableMap.of("form", emailForm)));
    }

    private ResetPasswordEmailForm buildPasswordResetEmailForm(String ipAddress, KupraUser user,
                                                               String token)
            throws UnsupportedEncodingException {
        final ResetPasswordEmailForm emailForm = new ResetPasswordEmailForm();
        emailForm.setUsername(user.getUserId());
        emailForm.setName(user.getProfile().getName());
        emailForm.setSurname(user.getProfile().getSurname());
        emailForm.setRequestDate(new Date());
        emailForm.setResetUrl(rootUrl+"/resetPassword/change?token="+ URLEncoder.encode(token, "UTF-8"));
        emailForm.setIpAddress(ipAddress);
        return emailForm;
    }
}
