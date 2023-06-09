package com.redscooter.API.order.OrderStatusChanged;

import com.redscooter.API.appUser.AppUserService;
import com.redscooter.API.common.mailSender.EmailSender;
import com.redscooter.API.order.Order;
import com.redscooter.exceptions.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusChangedListener implements ApplicationListener<OnOrderStatusChangedEvent> {

    @Autowired
    EmailSender emailSender;
    @Autowired
    private AppUserService appUserService;

    @Override
    public void onApplicationEvent(OnOrderStatusChangedEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnOrderStatusChangedEvent event) {
//        try {
            emailSender.sendEmailWithDefaultExceptionHandler(event.getRecipientAddress(), event.getSubject(), event.getHtml());
//        } catch (Exception ex) {
//            if (ex instanceof BaseException ex_)
//                ex_.printRootStackTrace();
//            else
//                ex.printStackTrace();
//        }
    }
}