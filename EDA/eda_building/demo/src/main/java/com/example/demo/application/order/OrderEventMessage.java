package com.example.demo.application.order;

import com.example.demo.application.event.EventRecordMessage;
import lombok.Getter;

@Getter
public class OrderEventMessage extends EventRecordMessage {

    private final long orderId;

    public OrderEventMessage(Object source, long orderId) {
        super(source);
        this.orderId = orderId;
    }

}
