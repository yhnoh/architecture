package com.example.demo.application.order;

import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final SnsTemplate snsTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void orderListener(OrderEventMessage orderEventMessage) {
        snsTemplate.convertAndSend("TEST_ER", orderEventMessage);
    }

    @SqsListener("TEST_ER")
    public void sendNotificationListener(@Headers Map<String, String> headers, @Payload String orderEventMessage) {
        System.out.println("주문 이벤트 발생이후 알람 전송");
    }

}
