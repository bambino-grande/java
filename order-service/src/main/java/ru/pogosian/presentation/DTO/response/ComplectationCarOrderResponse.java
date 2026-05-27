package ru.pogosian.presentation.DTO.response;

import ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage;

import java.util.UUID;

public record ComplectationCarOrderResponse(
    UUID orderId,
    UUID carId,
    UUID clientId,
    UUID managerId,
    ComplectationCarOrderStage stage
) {};
