package ru.pogosian.presentation.DTO.request;

import java.util.UUID;

public record UpdateComplectationCarOrderRequest(
    UUID carId,
    UUID clientId,
    UUID managerId,
    ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage stage
) {};
