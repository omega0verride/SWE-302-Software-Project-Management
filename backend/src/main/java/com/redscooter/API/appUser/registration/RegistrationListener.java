package com.redscooter.API.appUser.registration;

import com.redscooter.API.appUser.AppUser;
import com.redscooter.API.appUser.AppUserService;
import com.redscooter.API.common.mailSender.EmailSender;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URL;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    EmailSender emailSender;
    @Autowired
    private AppUserService appUserService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        VerificationToken verificationToken = event.getVerificationToken();
        AppUser user = verificationToken.getUser();

        UriComponents uriComponents = ServletUriComponentsBuilder.fromRequestUri(event.getHttpServletRequest()).build();
        String port = uriComponents.getPort() == -1 ? "" : ":" + uriComponents.getPort();
        String confirmationURL = uriComponents.getScheme() + "://" + uriComponents.getHost() + port + "/api/users/register/confirm?token=" + verificationToken.getToken() + "&username=" + user.getUsername();
        String recipientAddress = user.getUsername();
        String subject = "Konfirmimi i regjistrimit ne RedScooter.";
        emailSender.sendEmailWithDefaultExceptionHandler(recipientAddress, subject, ConfirmUserRegistrationHTMLBuilder.buildConfirmUserRegistrationHTML(user, confirmationURL, verificationToken));
    }
}