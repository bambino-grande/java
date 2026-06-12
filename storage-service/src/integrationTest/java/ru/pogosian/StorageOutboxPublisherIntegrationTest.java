package ru.pogosian;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pogosian.business.outbox.OutboxEvent;
import ru.pogosian.business.outbox.OutboxStatus;
import ru.pogosian.business.repositories.OutboxEventRepository;
import ru.pogosian.config.RabbitMQConfig;
import ru.pogosian.infrastructure.repository.JpaEntity.OutboxEventJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaOutboxEventRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class StorageOutboxPublisherIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Autowired
    private JpaOutboxEventRepository jpaOutboxEventRepository;


    @Test
    void shouldSavePendingOutboxEvent(){
        UUID eventId = UUID.randomUUID();
        outboxEventRepository.save(new OutboxEvent(
                eventId,
                UUID.randomUUID(),
                RabbitMQConfig.ROUTING_KEY_APPROVED,
                "{}",
                UUID.randomUUID(),
                OutboxStatus.PENDING,
                0
        ));

        OutboxEventJpaEntity event = jpaOutboxEventRepository.findById(eventId).orElseThrow();
        Assertions.assertEquals(OutboxStatus.PENDING, event.getOutboxStatus());
        Assertions.assertEquals(RabbitMQConfig.ROUTING_KEY_APPROVED, event.getRoutingKey());
    }
}
