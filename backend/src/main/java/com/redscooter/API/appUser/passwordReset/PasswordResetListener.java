package com.redscooter.API.appUser.passwordReset;

import com.redscooter.API.appUser.AppUser;
import com.redscooter.API.appUser.AppUserService;
import com.redscooter.API.common.mailSender.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetListener implements ApplicationListener<OnResetPasswordEvent> {

    @Autowired
    EmailSender emailSender;
    @Autowired
    private AppUserService appUserService;
    @Value("${server.domain}")
    private String domainName;
    @Override
    public void onApplicationEvent(OnResetPasswordEvent event) {
        this.resetPassword(event);
    }

    private void resetPassword(OnResetPasswordEvent event) {
        PasswordResetToken passwordResetToken = event.getPasswordResetToken();
        AppUser user = passwordResetToken.getUser();
        String confirmationURL = domainName+"resetPassword?token=" + passwordResetToken.getToken() + "&username=" + user.getUsername();
        String recipientAddress = user.getUsername();
        String subject = "RedScooter - Ndryshim i Fjalëkalimit";
        emailSender.sendEmailWithDefaultExceptionHandler(recipientAddress, subject, ResetPasswordHTMLBuilder.buildResetPasswordHTML(user, confirmationURL, passwordResetToken));
    }
}