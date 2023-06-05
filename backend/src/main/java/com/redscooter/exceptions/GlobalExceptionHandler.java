package com.redscooter.exceptions;

import com.redscooter.exceptions.mailSender.FailedToSendEmailException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> invalidCriteriaOperatorExceptionHandler(BaseException ex) {
        ex.printRootStackTrace();
        return ex.toResponseEntity();
    }

    @ExceptionHandler(FailedToSendEmailException.class)
    public ResponseEntity<Object> invalidCriteriaOperatorExceptionHandler(FailedToSendEmailException ex) {
        ex.printRootStackTrace();
        return ex.toResponseEntity();
    }

    @ExceptionHandler(org.restprocessors.exceptions.BaseException.class)
    public ResponseEntity<Object> invalidCriteriaOperatorExceptionHandler(org.restprocessors.exceptions.BaseException ex) {
        ex.printRootStackTrace();
        return ex.toResponseEntity();
    }


}
