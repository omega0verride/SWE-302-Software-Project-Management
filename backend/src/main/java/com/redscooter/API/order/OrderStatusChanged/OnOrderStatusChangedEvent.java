package com.redscooter.API.order.OrderStatusChanged;

import com.redscooter.API.order.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnOrderStatusChangedEvent extends ApplicationEvent {
    private Order order;
    private String adminNotesOnStatusChange;

    public OnOrderStatusChangedEvent(Order order) {
        this(order, null);
    }

    public OnOrderStatusChangedEvent(Order order, String adminNotesOnStatusChange) {
        super(order);
        this.order = order;
        this.adminNotesOnStatusChange = adminNotesOnStatusChange;
    }
}