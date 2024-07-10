package com.example.demo.application.order;

import com.example.demo.application.event.EventRecordMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void order(String goodsName) {
        OrderJpaEntity orderJpaEntity = OrderJpaEntity.builder().goodsName(goodsName).build();
        orderJpaRepository.save(orderJpaEntity);

        OrderEventPayload orderEventPayload = new OrderEventPayload(orderJpaEntity.getId());
        EventRecordMessage<OrderEventPayload> eventRecordMessage = new EventRecordMessage<>("TEST_ER", orderEventPayload);
        applicationEventPublisher.publishEvent(eventRecordMessage);

    }
}
