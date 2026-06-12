package ru.pogosian.presentation.DTO.request;

import ru.pogosian.presentation.DTO.Types.InStockCarOrderStage;

import java.util.UUID;

public record UpdateInStockCarOrderRequest(
    UUID carId,
    UUID clientId,
    UUID managerId,
    InStockCarOrderStage stage
) {
}
