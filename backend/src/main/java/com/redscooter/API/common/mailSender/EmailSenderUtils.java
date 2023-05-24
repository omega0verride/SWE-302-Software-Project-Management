package com.redscooter.API.common.mailSender;

import org.springframework.mail.MailSendException;

import jakarta.mail.SendFailedException;

public class EmailSenderUtils {
//    public static void handleException(Exception ex) {
//        if (ex instanceof SendFailedException sendFailedException) {
//            throw new FailedToSendEmailException(sendFailedException);
//        } else if (ex instanceof MessagingException messagingException) {
//            throw ex;
//        } else if (ex instanceof MailSendException mailSendException) {
//            SendFailedException sendFailedException = getSendFailedExceptionFromMailSendException(mailSendException);
//            throw new FailedToSendEmailException(sendFailedException);
//        } else {
//            throw new FailedToSendEmailException(ex);
//        }
//    }


    public static SendFailedException getSendFailedExceptionFromMailSendException(MailSendException mailSendException) {
        try {
            return (SendFailedException) mailSendException.getFailedMessages().entrySet().iterator().next().getValue();
        } catch (Exception exception) {
            return null;
        }
    }
}