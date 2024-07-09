package com.example.demo.application.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EventRecordListener {

    private final EventRecordService eventRecordService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveEventRecordListener(EventRecordMessage eventRecordMessage) throws JsonProcessingException {
        eventRecordService.save(eventRecordMessage);
        System.out.println("주문 이벤트 전 이벤트 레코드 등록");
    }

    @SqsListener("HELLO_ER")
    public void updateEventRecordListener(EventRecordMessage eventRecordMessage) {
        eventRecordService.update(eventRecordMessage.getEventRecordId());
        System.out.println("주문 이벤트 발생이후 이벤트 레코드 업데이트");
    }
}
