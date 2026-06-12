package ru.pogosian.messaging.events;

import ru.pogosian.messaging.OrderType;

import java.util.UUID;

public record OrderSentForApproval(
        UUID messageId,
        UUID orderId,
        OrderType orderType,
        UUID traceId,
        UUID carId
) {
}
