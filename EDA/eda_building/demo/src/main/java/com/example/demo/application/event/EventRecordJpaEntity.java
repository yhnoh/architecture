package com.example.demo.application.event;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_record")
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class EventRecordJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "published")
    private boolean published = false;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "event_payload")
    private String eventPayload;

    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    void setEventPayload(String eventPayload) {
        this.eventPayload = eventPayload;
    }
}
