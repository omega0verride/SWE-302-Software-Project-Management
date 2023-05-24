package com.redscooter.API.common.mailSender;

import com.redscooter.config.configProperties.EmailConfigProperties;
import com.redscooter.exceptions.mailSender.FailedToSendEmailException;
import com.redscooter.exceptions.mailSender.UnknownEmailSenderException;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    @Autowired
    EmailConfigProperties emailConfigProperties;

    @Autowired
    private MessageSource messages;
    @Autowired
    private JavaMailSender mailSender;


    public void sendEmailWithDefaultExceptionHandler(String recipientAddress, String subject, String content) {
        try {
            sendEmail(recipientAddress, subject, content);
        } catch (SendFailedException ex) {
            throw new FailedToSendEmailException(ex);
        } catch (MailSendException ex) {
            SendFailedException sendFailedException = EmailSenderUtils.getSendFailedExceptionFromMailSendException(ex);
            if (sendFailedException == null)
                throw new UnknownEmailSenderException(recipientAddress);
            throw new FailedToSendEmailException(sendFailedException);
        } catch (Exception ex) {
            throw new UnknownEmailSenderException(recipientAddress);
        }
    }

    public void sendEmail(String recipientAddress, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject(subject);
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(emailConfigProperties.getUsername());
        helper.setTo(recipientAddress);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
