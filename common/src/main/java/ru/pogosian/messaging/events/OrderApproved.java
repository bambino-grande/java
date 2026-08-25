package ru.pogosian.messaging.events;

import ru.pogosian.messaging.OrderType;

import java.util.UUID;

public record OrderApproved(
        UUID messageId,
        UUID orderId,
        OrderType orderType,
        UUID traceId
) {
}
