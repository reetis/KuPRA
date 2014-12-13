package eu.komanda30.kupra.email;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
                LOG.debug("Mocking sending email from:\n{} \nto:\n{} \nbody:\n{}", msg.getFrom(),
                        msg.getAllRecipients(), contentToString(msg.getContent()));
            }
        } catch (final Exception e) {
            throw new MailPreparationException(e);
        }
    }

    private String contentToString(Object content)
            throws IOException, MessagingException {
        if (content instanceof MimeMultipart) {
            final StringBuilder builder = new StringBuilder();
            final MimeMultipart multipart = (MimeMultipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                final BodyPart part = multipart.getBodyPart(i);
                builder.append("Part ").append(i).append(": {\n")
                        .append(contentToString(part.getContent())).append("\n}\n");
            }
            return builder.toString();
        } else {
            return content.toString();
        }
    }
}
