package com.example.demo.application.order;

import com.example.demo.application.event.EventRecordMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEventMessage extends EventRecordMessage {

    private long orderId;

    public OrderEventMessage(long orderId) {
        this.orderId = orderId;
    }
}
