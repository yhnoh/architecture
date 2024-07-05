package com.example.demo.application.event;

import org.springframework.data.jpa.repository.JpaRepository;

interface EventRecordJpaRepository extends JpaRepository<EventRecordJpaEntity, Long> {
}
