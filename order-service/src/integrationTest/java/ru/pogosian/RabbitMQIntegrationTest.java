package ru.pogosian;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.awaitility.Awaitility;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrderIsReadyForPickingUp;
import ru.pogosian.business.repositories.InStockCarOrderRepository;
import ru.pogosian.business.repositories.ProcessedEventRepository;
import ru.pogosian.business.services.OrderService;
import ru.pogosian.config.RabbitMQConfig;
import ru.pogosian.infrastructure.messaging.OrderEventConsumer;
import ru.pogosian.messaging.OrderType;
import ru.pogosian.messaging.events.OrderApproved;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RabbitMQIntegrationTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private InStockCarOrderRepository inStockCarOrderRepository;

    @Autowired
    private OrderEventConsumer orderEventConsumer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProcessedEventRepository processedEventRepository;


    @Test
    public void shouldReceiveApprovedEventFromRabbitAndMarkOrderReadyForPickup() throws JsonProcessingException {
        InStockCarOrder order = orderService.createInStockCarOrder(UUID.fromString("40000000-0000-0000-0000-000000000001"), UUID.fromString("10000000-0000-0000-0000-000000000001"));

        OrderApproved event = new OrderApproved(
                UUID.randomUUID(),
                order.getOrderId(),
                OrderType.IN_STOCK,
                UUID.randomUUID()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY_APPROVED,
                objectMapper.writeValueAsBytes(event)
        );

        Awaitility.await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(() -> {
                    InStockCarOrder updatedOrder = inStockCarOrderRepository.findById(order.getOrderId());
                    assertInstanceOf(InStockCarOrderIsReadyForPickingUp.class, updatedOrder.getState());
                    assertTrue(processedEventRepository.existsByEventId(event.messageId()));
                });
    }
}
