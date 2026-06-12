package ru.pogosian.presentation.DTO.request;

import ru.pogosian.business.cars.ColorTypes;

import java.awt.*;
import java.util.UUID;

public record CreateOrUpdateCarRequest(
        String carName,
        UUID configurationId,
        ColorTypes color,
        Boolean availableForSale,
        Boolean availableForTestDrive
        ) {}