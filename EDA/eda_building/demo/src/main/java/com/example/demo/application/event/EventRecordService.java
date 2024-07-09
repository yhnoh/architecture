package com.example.demo.application.event;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventRecordService {

    private final EventRecordJpaRepository eventRecordJpaRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void save(EventRecordMessage eventRecordMessage) throws JsonProcessingException {

        EventRecordJpaEntity eventRecordJpaEntity = EventRecordJpaEntity.builder()
                .published(false)
                .publishedAt(eventRecordMessage.getPublishedAt())
                .build();

        eventRecordJpaRepository.save(eventRecordJpaEntity);
        eventRecordMessage.setEventRecordId(eventRecordJpaEntity.getId());

        String eventPayload = objectMapper.writeValueAsString(eventRecordMessage);
        eventRecordJpaEntity.setEventPayload(eventPayload);
    }


    @Transactional
    public void update(long eventRecordId) {
        eventRecordJpaRepository.findById(eventRecordId)
                .ifPresent(eventRecordJpaEntity -> {
                    eventRecordJpaEntity.publish();
                });
    }


/*
    @Transactional
    public void save(EventRecordMessage eventRecordMessage) throws JsonProcessingException {

        EventRecordJpaEntity eventRecordJpaEntity = EventRecordJpaEntity.builder()
                .published(false)
                .publishedAt(eventRecordMessage.getPublishedAt())
                .build();

        eventRecordJpaRepository.save(eventRecordJpaEntity);
        eventRecordMessage.setEventRecordId(eventRecordJpaEntity.getId());

        String eventPayload = objectMapper.writeValueAsString(eventRecordMessage);
        eventRecordJpaEntity.setEventPayload(eventPayload);


        throw new IllegalArgumentException("예외 발생시 정상 롤백하는지 확인");
    }

    */

}
