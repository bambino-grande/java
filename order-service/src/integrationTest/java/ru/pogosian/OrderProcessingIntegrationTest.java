package ru.pogosian;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrderIsReadyForPickingUp;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrderPlaced;
import ru.pogosian.business.repositories.InStockCarOrderRepository;
import ru.pogosian.business.repositories.ProcessedEventRepository;
import ru.pogosian.business.services.OrderService;
import ru.pogosian.infrastructure.messaging.OrderEventConsumer;
import ru.pogosian.messaging.OrderType;
import ru.pogosian.messaging.events.OrderApproved;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderProcessingIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private InStockCarOrderRepository inStockCarOrderRepository;

    @Autowired
    private OrderEventConsumer orderEventConsumer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProcessedEventRepository processedEventRepository;


    @Test
    public void shouldSuccessfullyCreateAndProceedInStockOrder() throws IOException {
        InStockCarOrder order = orderService.createInStockCarOrder(UUID.fromString("40000000-0000-0000-0000-000000000001"), UUID.fromString("10000000-0000-0000-0000-000000000001"));
        orderService.moveInStockCarOrder(order.getOrderId());
        orderService.moveInStockCarOrder(order.getOrderId());
        orderService.moveInStockCarOrder(order.getOrderId());

        OrderApproved event = new OrderApproved(
                UUID.randomUUID(),
                order.getOrderId(),
                OrderType.IN_STOCK,
                UUID.randomUUID()
        );

        orderEventConsumer.handleOrderApproved(objectMapper.writeValueAsBytes(event));

        InStockCarOrder updatedOrder = inStockCarOrderRepository.findById(order.getOrderId());
        assertInstanceOf(InStockCarOrderIsReadyForPickingUp.class, updatedOrder.getState());
        assertTrue(processedEventRepository.existsByEventId(event.messageId()));

        orderEventConsumer.handleOrderApproved(objectMapper.writeValueAsBytes(event));
        updatedOrder = inStockCarOrderRepository.findById(order.getOrderId());
        assertInstanceOf(InStockCarOrderIsReadyForPickingUp.class, updatedOrder.getState());
    }
}
