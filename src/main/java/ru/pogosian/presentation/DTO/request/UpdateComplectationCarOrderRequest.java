package ru.pogosian.presentation.DTO.request;

import ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage;

import java.util.UUID;

public record UpdateComplectationCarOrderRequest(
    UUID carId,
    UUID clientId,
    UUID managerId,
    ComplectationCarOrderStage stage
) {};
