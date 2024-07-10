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
                .eventName(eventRecordMessage.getEventName())
                .build();

        eventRecordJpaRepository.save(eventRecordJpaEntity);

        //이벤트 전송 전 eventRecordId를 등록하여 전송
        eventRecordMessage.setEventRecordId(eventRecordJpaEntity.getId());

        //eventPayload 객체를 JSON 문자열로 변환하여 저장
        String eventPayload = objectMapper.writeValueAsString(eventRecordMessage.getEventPayload());
        eventRecordJpaEntity.setEventPayload(eventPayload);
    }


    @Transactional
    public void update(long eventRecordId) {
        eventRecordJpaRepository.findById(eventRecordId)
                .ifPresent(eventRecordJpaEntity -> {
                    eventRecordJpaEntity.published();
                });
    }
}
