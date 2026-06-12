package ru.pogosian.presentation.DTO.request;

import java.util.UUID;

public record CreateInStockCarOrderRequest(
    UUID carId,
    UUID clientId
    ) {
}
