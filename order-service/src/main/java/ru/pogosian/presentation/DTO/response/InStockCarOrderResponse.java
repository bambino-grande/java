package ru.pogosian.presentation.DTO.response;

import ru.pogosian.presentation.DTO.Types.InStockCarOrderStage;

import java.util.UUID;

public record InStockCarOrderResponse(
    UUID orderId,
    UUID carId,
    UUID clientId,
    UUID managerId,
    InStockCarOrderStage stage
) {
}
