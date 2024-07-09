package com.example.demo.application.event;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventRecordMessage {

    @Setter(AccessLevel.PACKAGE)
    private long eventRecordId;
    private final LocalDateTime publishedAt = LocalDateTime.now();

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

}
