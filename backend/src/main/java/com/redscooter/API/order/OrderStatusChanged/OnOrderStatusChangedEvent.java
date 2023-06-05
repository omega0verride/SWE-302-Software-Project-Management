package com.redscooter.API.order.OrderStatusChanged;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnOrderStatusChangedEvent extends ApplicationEvent {
    private String recipientAddress;
    private String subject;
    private String html;

    public OnOrderStatusChangedEvent(String recipientAddress, String subject, String html) {
        super(html);
        this.recipientAddress=recipientAddress;
        this.subject=subject;
        this.html=html;
    }
}