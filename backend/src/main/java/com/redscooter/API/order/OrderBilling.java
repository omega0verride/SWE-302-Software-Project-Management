package com.redscooter.API.order;


import com.redscooter.API.order.DTO.CreateOrderBillingDTO;
import com.redscooter.API.order.DTO.GetOrderBillingDTO;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBilling {
    private String clientName;

    private String clientSurname;

    private String clientPhoneNumber;

    private String clientEmail;

    private String clientAddressLine1;

    private String clientNotes;

    private PaymentOption paymentOption;

    public OrderBilling(CreateOrderBillingDTO createOrderBillingDTO) {
        setClientName(createOrderBillingDTO.getClientName());
        setClientSurname(createOrderBillingDTO.getClientSurname());
        setClientPhoneNumber(createOrderBillingDTO.getClientPhoneNumber());
        setClientEmail(createOrderBillingDTO.getClientEmail());
        setClientAddressLine1(createOrderBillingDTO.getClientAddressLine1());
        setClientNotes(createOrderBillingDTO.getClientNotes());
        setPaymentOption(createOrderBillingDTO.getPaymentOption());
    }

    public GetOrderBillingDTO toGetOrderBillingDTO() {
        return toGetOrderBillingDTO(this);
    }

    public static GetOrderBillingDTO toGetOrderBillingDTO(OrderBilling orderBilling) {
        return new GetOrderBillingDTO(orderBilling);
    }
}
