package ru.pogosian.presentation.DTO.response;

import java.time.Instant;
import java.util.UUID;

public record AssemblyOrderResponse(
        UUID id,
        UUID sourceOrderId,
        Instant createdAt,
        Instant updatedAt,
        String status,
        boolean removed
) {
}
