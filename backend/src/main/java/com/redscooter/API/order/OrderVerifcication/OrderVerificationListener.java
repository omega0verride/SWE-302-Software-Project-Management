package com.redscooter.API.order.OrderVerifcication;

import com.redscooter.API.common.mailSender.EmailSender;
import com.redscooter.API.common.mailSender.EmailSenderUtils;
import com.redscooter.API.order.Order;
import com.redscooter.exceptions.mailSender.FailedToSendEmailException;
import com.redscooter.exceptions.mailSender.UnknownEmailSenderException;
import jakarta.mail.SendFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

@Component
public class OrderVerificationListener implements ApplicationListener<OnOrderConfirmedEvent> {

    @Autowired
    EmailSender emailSender;

    @Override
    public void onApplicationEvent(OnOrderConfirmedEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnOrderConfirmedEvent event) {
        Order order = event.getOrder();
        String recipientAddress = order.getOrderBilling().getClientEmail();
        String recipientName = order.getOrderBilling().getClientName();
        String subject = "Konfirmimi i porosisë ne RedScooter";
        try {
            emailSender.sendEmail(recipientAddress, subject, OrderConfirmationHTMLBuilder.buildOrderConfirmationHTML(order));
        } catch (MailSendException ex) { // TODO how to handle cases when email not sent
            SendFailedException sendFailedException = EmailSenderUtils.getSendFailedExceptionFromMailSendException(ex);
            if (sendFailedException == null)
                throw new UnknownEmailSenderException(recipientAddress);
            throw new FailedToSendEmailException(sendFailedException);
        } catch (Exception ex) {
            throw new UnknownEmailSenderException(recipientAddress);
        }
    }
}