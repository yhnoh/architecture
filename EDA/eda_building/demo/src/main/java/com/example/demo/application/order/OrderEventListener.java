package com.example.demo.application.order;

import com.example.demo.application.event.EventRecordMessage;
import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final SnsTemplate snsTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void orderListener(EventRecordMessage<OrderEventPayload> eventRecordMessage) {
        snsTemplate.convertAndSend(eventRecordMessage.getEventName(), eventRecordMessage);
    }

    @SqsListener("TEST_ER")
    public void sendNotificationListener(@Payload String message) {
        System.out.println("주문 이벤트 전송 후 알람 전송, message = " + message);
    }

}
