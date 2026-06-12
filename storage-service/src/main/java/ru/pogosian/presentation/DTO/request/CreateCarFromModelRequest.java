package ru.pogosian.presentation.DTO.request;

import ru.pogosian.business.cars.ColorTypes;

import java.util.UUID;

public record CreateCarFromModelRequest(
        UUID carModelId,
        String carName,
        ColorTypes color,
        Boolean availableForSale,
        Boolean availableForTestDrive
) {
}