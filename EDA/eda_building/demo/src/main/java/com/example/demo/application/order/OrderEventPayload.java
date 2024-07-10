package com.example.demo.application.order;

import lombok.Getter;


@Getter
public class OrderEventPayload {

    private final long orderId;

    public OrderEventPayload(long orderId) {
        this.orderId = orderId;
    }

}
