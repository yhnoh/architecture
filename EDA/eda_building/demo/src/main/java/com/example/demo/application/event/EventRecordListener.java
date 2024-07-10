package com.example.demo.application.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventRecordListener {

    private final EventRecordService eventRecordService;

    @EventListener
    public void saveEventRecordListener(EventRecordMessage eventRecordMessage) throws JsonProcessingException {
        eventRecordService.save(eventRecordMessage);
        System.out.println("주문 이벤트 전송 전 이벤트 레코드 등록");
    }

    @SqsListener("HELLO_ER")
    public void updateEventRecordListener(@Payload EventRecordPayload eventRecordPayload) {
        eventRecordService.update(eventRecordPayload.getEventRecordId());
        System.out.println("주문 이벤트 전송 후 이벤트 레코드 업데이트");
    }
}
