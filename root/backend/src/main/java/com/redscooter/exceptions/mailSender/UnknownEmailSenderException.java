package com.redscooter.exceptions.mailSender;

import org.springframework.http.HttpStatus;

public class UnknownEmailSenderException extends EmailSenderException {
    public String recipientAddress;

    public UnknownEmailSenderException(String recipientAddress) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong and we were not able to send the email to [" + recipientAddress + "].");
    }

    public UnknownEmailSenderException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
