package com.example.demo.application.event;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public abstract class EventRecordMessage extends ApplicationEvent {

    @Setter(AccessLevel.PACKAGE)
    private long eventRecordId;

    public EventRecordMessage(Object source) {
        super(source);
    }

    public LocalDateTime getPublishedAt() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(super.getTimestamp()), ZoneId.systemDefault());
    }


}
