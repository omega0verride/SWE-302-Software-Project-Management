package com.redscooter.API.appUser.passwordReset;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnResetPasswordEvent extends ApplicationEvent {
    private PasswordResetToken passwordResetToken;
    private HttpServletRequest httpServletRequest;

    public OnResetPasswordEvent(HttpServletRequest httpServletRequest, PasswordResetToken passwordResetToken) {
        super(passwordResetToken);
        this.passwordResetToken = passwordResetToken;
        this.httpServletRequest = httpServletRequest;
    }
}