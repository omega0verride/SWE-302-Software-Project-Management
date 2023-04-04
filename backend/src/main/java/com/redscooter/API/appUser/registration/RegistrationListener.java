//package com.redscooter.API.appUser.registration;
//
//import com.redscooter.API.appUser.AppUser;
//import com.redscooter.API.appUser.AppUserService;
//import com.redscooter.API.common.mailSender.EmailSender;
//import com.redscooter.API.store.StoreService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//@Component
//public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
//
//    @Autowired
//    EmailSender emailSender;
//    @Autowired
//    private AppUserService appUserService;
//    @Autowired
//    private StoreService storeService;
//
//    @Override
//    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
//        this.confirmRegistration(event);
//    }
//
//    private void confirmRegistration(OnRegistrationCompleteEvent event) {
//        VerificationToken verificationToken = event.getVerificationToken();
//        AppUser user = verificationToken.getUser();
//        String confirmationURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/api/public/users/register/confirm?token=" + verificationToken.getToken() + "&username=" + user.getUsername();
//        String recipientAddress = user.getUsername();
//        String subject = "Konfirmimi i regjistrimit ne RedScooter.";
//        emailSender.sendEmailWithDefaultExceptionHandler(recipientAddress, subject, ConfirmUserRegistrationHTMLBuilder.buildConfirmUserRegistrationHTML(user, confirmationURL, verificationToken, storeService.getByName("redscooter", false)));
//    }
//}