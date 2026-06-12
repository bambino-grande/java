package ru.pogosian.infrastructure.repository.Mapper;

import org.springframework.stereotype.Component;
import ru.pogosian.business.outbox.OutboxEvent;
import ru.pogosian.infrastructure.repository.JpaEntity.OutboxEventJpaEntity;

@Component
public class OutboxEventMapper {
    public OutboxEvent toDomain(OutboxEventJpaEntity entity) {
        return new OutboxEvent(
                entity.getId(),
                entity.getAggregateId(),
                entity.getRoutingKey(),
                entity.getMessage(),
                entity.getTraceId(),
                entity.getOutboxStatus(),
                entity.getAttempts()
        );
    }

    public OutboxEventJpaEntity toJpaEntity(OutboxEvent domain) {
        return new OutboxEventJpaEntity(
                domain.getId(),
                domain.getAggregateId(),
                domain.getRoutingKey(),
                domain.getMessage(),
                domain.getTraceId(),
                domain.getOutboxStatus(),
                domain.getTryCount()
        );
    }
}
