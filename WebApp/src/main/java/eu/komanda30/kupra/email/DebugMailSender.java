package eu.komanda30.kupra.email;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class DebugMailSender extends JavaMailSenderImpl {
    private final static Logger LOG = LoggerFactory.getLogger(DebugMailSender.class);

    @Override
    public void send(final MimeMessagePreparator mimeMessagePreparator) throws MailException {
        final MimeMessage mimeMessage = createMimeMessage();
        try {
            mimeMessagePreparator.prepare(mimeMessage);
            final String content = (String) mimeMessage.getContent();
            final Properties javaMailProperties = getJavaMailProperties();
            javaMailProperties.setProperty("mailContent", content);

            LOG.debug("Mocking sending email from:\n{} to:\n{} body:\n{}", mimeMessage.getFrom(),
                    mimeMessage.getAllRecipients(), mimeMessage.getContent());
        } catch (final Exception e) {
            throw new MailPreparationException(e);
        }
    }
}
