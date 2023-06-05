package com.redscooter.API.appUser.registration;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private VerificationToken verificationToken;
//    private HttpServletRequest httpServletRequest;

    public OnRegistrationCompleteEvent(VerificationToken verificationToken) {
        super(verificationToken);
        this.verificationToken = verificationToken;
//        this.httpServletRequest = httpServletRequest;
    }
}