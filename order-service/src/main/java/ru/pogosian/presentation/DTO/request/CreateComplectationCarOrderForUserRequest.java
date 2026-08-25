package ru.pogosian.presentation.DTO.request;

import java.util.UUID;

public record CreateComplectationCarOrderForUserRequest(
    UUID carId
) {
}
