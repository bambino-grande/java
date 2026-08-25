package ru.pogosian.presentation.DTO.response;

import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

public record AvailableCarResponse(
        UUID carId,
        String carName,
        UUID configurationId,
        UUID configurationModelId,
        String color,
        BigDecimal price,
        Boolean availableForSale,
        Boolean availableForTestDrive
) {
}
