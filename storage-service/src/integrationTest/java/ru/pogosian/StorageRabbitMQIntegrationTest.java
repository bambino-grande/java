package ru.pogosian;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import ru.pogosian.business.assembly.AssemblyOrder;
import ru.pogosian.business.repositories.AssemblyOrderRepository;
import ru.pogosian.config.RabbitMQConfig;
import ru.pogosian.infrastructure.repository.JpaEntity.OutboxEventJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaOutboxEventRepository;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaProcessedEventRepository;
import ru.pogosian.messaging.OrderType;
import ru.pogosian.messaging.events.OrderSentForApproval;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageRabbitMQIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AssemblyOrderRepository assemblyOrderRepository;

    @Autowired
    private JpaProcessedEventRepository processedEventRepository;

    @Autowired
    private JpaOutboxEventRepository outboxEventRepository;

    @Test
    public void shouldReceiveOrderSentForApprovalFromRabbitAndCreateAssemblyOrder() throws JsonProcessingException {
        UUID orderId = UUID.randomUUID();
        OrderSentForApproval event = new OrderSentForApproval(
                UUID.randomUUID(),
                orderId,
                OrderType.IN_STOCK,
                UUID.randomUUID(),
                UUID.fromString("40000000-0000-0000-0000-000000000001")
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY_SENT_FOR_APPROVAL,
                objectMapper.writeValueAsBytes(event)
        );

        Awaitility.await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(() -> {
                    AssemblyOrder assemblyOrder = assemblyOrderRepository.findAll(PageRequest.of(0, 100)).getFirst();
                    assertEquals(orderId, assemblyOrder.getSourceOrderId());
                    assertTrue(processedEventRepository.existsById(event.messageId()));

                    List<OutboxEventJpaEntity> outboxEvents = outboxEventRepository.findAll();
                    assertEquals(1, outboxEvents.size());

                    OutboxEventJpaEntity outboxEvent = outboxEvents.getFirst();
                    assertEquals(orderId, outboxEvent.getAggregateId());
                    }
                );
    }
}
