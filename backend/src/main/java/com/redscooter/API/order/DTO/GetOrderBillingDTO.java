package com.redscooter.API.order.DTO;

import com.redscooter.API.order.OrderBilling;
import com.redscooter.API.order.PaymentOption;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GetOrderBillingDTO {
    private String clientName;

    private String clientSurname;

    private String clientPhoneNumber;

    private String clientEmail;

    private String clientAddressLine1;

    private String clientNotes;

    private PaymentOption paymentOption;

    public GetOrderBillingDTO(OrderBilling orderBilling) {
        setClientName(orderBilling.getClientName());
        setClientSurname(orderBilling.getClientSurname());
        setClientPhoneNumber(orderBilling.getClientPhoneNumber());
        setClientEmail(orderBilling.getClientEmail());
        setClientAddressLine1(orderBilling.getClientAddressLine1());
        setClientNotes(orderBilling.getClientNotes());
        setPaymentOption(orderBilling.getPaymentOption());
    }
}
