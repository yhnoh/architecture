package com.example.demo.application.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EventRecordListener {

    private final EventRecordService eventRecordService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void eventRecordListener(EventRecordMessage eventRecordMessage) throws JsonProcessingException {
        eventRecordService.save(eventRecordMessage);
    }
}
