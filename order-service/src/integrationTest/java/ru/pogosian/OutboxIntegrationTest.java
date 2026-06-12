package ru.pogosian;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.outbox.OutboxEvent;
import ru.pogosian.business.repositories.OutboxEventRepository;
import ru.pogosian.business.services.OrderService;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class OutboxIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Test
    void movingOrderToPayedCreatesOutboxEvent() {
        InStockCarOrder inStockCarOrder = orderService.createInStockCarOrder(
                UUID.fromString("40000000-0000-0000-0000-000000000001"),
                UUID.fromString("10000000-0000-0000-0000-000000000001")
        );

        UUID orderId = inStockCarOrder.getOrderId();

        orderService.moveInStockCarOrder(orderId);
        orderService.moveInStockCarOrder(orderId);
        orderService.moveInStockCarOrder(orderId);

        List<OutboxEvent> events = outboxEventRepository.findPendingForUpdate();
        Assertions.assertEquals(1, events.size());
    }
}
