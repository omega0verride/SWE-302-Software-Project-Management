package com.redscooter.API.order.DTO;

import com.redscooter.API.common.confirmationToken.ConfirmationToken;
import com.redscooter.API.order.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderConfirmationTokenDTO {
    public String confirmationToken;
    public Double orderTotal;
    public String clientEmail;

    public OrderConfirmationTokenDTO(ConfirmationToken<Order> confirmationToken) {
        this.confirmationToken = confirmationToken.getToken();
        this.orderTotal = confirmationToken.getObject().calculateTotal();
        this.clientEmail = confirmationToken.getObject().getOrderBilling().getClientEmail();
    }
}
