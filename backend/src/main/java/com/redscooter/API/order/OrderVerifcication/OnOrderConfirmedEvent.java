package com.redscooter.API.order.OrderVerifcication;

import com.redscooter.API.order.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnOrderConfirmedEvent extends ApplicationEvent {
    private Order order;

    public OnOrderConfirmedEvent(Order order) {
        super(order);
        this.order = order;
    }
}