package hu.ponte.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Transactional
@Slf4j
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender javaMailSender;

    private static final String SEND_ERROR = "Error while sending email: ";

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendActivation(String to, String code) throws MailException {

        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.addTo(to);
            mimeMessageHelper.setSubject("Activation link");
            String body = "To activate your account please click on the following link.\n" +
                    "http://localhost:8080/api/users/activation/" + code;
            mimeMessageHelper.setText(body);
            javaMailSender.send(mimeMessage);

        } catch (MailException | MessagingException e) {
            log.info(SEND_ERROR + e.getMessage());
        }
    }

    public void sendPasswordResetLink(String to, String code) throws MailException {

        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.addTo(to);
            mimeMessageHelper.setSubject("Password reset link");
            String body = "To reset your password please click on the following link.\n" +
                    "http://localhost:8080/api/users/passwordReset/" + code;
            mimeMessageHelper.setText(body);
            javaMailSender.send(mimeMessage);

        } catch (MailException | MessagingException e) {
            log.info(SEND_ERROR + e.getMessage());
        }
    }
}
