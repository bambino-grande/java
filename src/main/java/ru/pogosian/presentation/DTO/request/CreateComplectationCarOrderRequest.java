package ru.pogosian.presentation.DTO.request;

import java.util.UUID;

public record CreateComplectationCarOrderRequest(
    UUID carId,
    UUID clientId,
    UUID managerId
) {
}
