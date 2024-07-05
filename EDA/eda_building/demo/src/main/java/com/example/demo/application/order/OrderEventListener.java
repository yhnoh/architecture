package com.example.demo.application.order;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendOrderEmail(OrderEventMessage orderEventMessage) {
        long eventRecordId = orderEventMessage.getEventRecordId();
        long orderId = orderEventMessage.getOrderId();

        System.out.println("eventRecordId = " + eventRecordId);
        System.out.println("orderId = " + orderId);
    }
}
