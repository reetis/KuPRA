package eu.komanda30.kupra.email;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class DebugMailSender extends JavaMailSenderImpl {
    private final static Logger LOG = LoggerFactory.getLogger(DebugMailSender.class);

    @Override
    public void send(MimeMessage[] mimeMessages) throws MailException {
        try {
            for (MimeMessage msg : mimeMessages) {
                LOG.debug("Mocking sending email from:\n{} to:\n{} body:\n{}", msg.getFrom(),
                        msg.getAllRecipients(), msg.getContent());
            }
        } catch (final Exception e) {
            throw new MailPreparationException(e);
        }
    }
}
