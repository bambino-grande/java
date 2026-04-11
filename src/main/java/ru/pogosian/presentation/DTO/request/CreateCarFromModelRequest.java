package ru.pogosian.presentation.DTO.request;

import java.awt.*;
import java.util.UUID;

public record CreateCarFromModelRequest(
        UUID carModelId,
        String carName,
        String color,
        Boolean availableForSale,
        Boolean availableForTestDrive
) {
}