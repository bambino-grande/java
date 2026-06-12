package ru.pogosian.presentation.DTO.request;

import java.util.UUID;

public record CreateOrUpdateAssemblyOrderRequest(
        UUID sourceOrderId,
        String status
) {
}
