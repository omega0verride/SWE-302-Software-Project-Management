package com.redscooter.API.appUser.passwordReset;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnResetPasswordEvent extends ApplicationEvent {
    private PasswordResetToken passwordResetToken;

    public OnResetPasswordEvent(PasswordResetToken passwordResetToken) {
        super(passwordResetToken);
        this.passwordResetToken = passwordResetToken;
    }
}