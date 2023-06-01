package com.redscooter.API.order.OrderStatusChanged;

import com.redscooter.API.appUser.AppUserService;
import com.redscooter.API.common.mailSender.EmailSender;
import com.redscooter.API.order.Order;
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
        Order order = event.getOrder();
        String recipientAddress = order.getOrderBilling().getClientEmail();
        String subject = "Porosia juaj #" + order.getId() + " u kalua ne statusin [" + order.getOrderStatus() + "]";
        emailSender.sendEmailWithDefaultExceptionHandler(recipientAddress, subject, OrderStatusHTMLBuilder.buildOrderStatusHTML(order, event.getAdminNotesOnStatusChange()));
    }
}