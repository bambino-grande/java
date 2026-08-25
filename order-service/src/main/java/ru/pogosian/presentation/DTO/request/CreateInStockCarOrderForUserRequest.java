package ru.pogosian.presentation.DTO.request;

import java.util.UUID;

public record CreateInStockCarOrderForUserRequest(
    UUID carId
    ) {
}
