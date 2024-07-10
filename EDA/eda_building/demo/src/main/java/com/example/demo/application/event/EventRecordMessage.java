package com.example.demo.application.event;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public final class EventRecordMessage<T> {

    @Setter(AccessLevel.PACKAGE)
    private long eventRecordId;
    private final String eventName;
    private final T eventPayload;
    private final LocalDateTime publishedAt = LocalDateTime.now();

    public EventRecordMessage(String eventName, T eventPayload) {
        this.eventName = eventName;
        this.eventPayload = eventPayload;
    }

}
