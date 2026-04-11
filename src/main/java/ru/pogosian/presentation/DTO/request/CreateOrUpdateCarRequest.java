package ru.pogosian.presentation.DTO.request;

import java.awt.*;
import java.util.UUID;

public record CreateOrUpdateCarRequest(
        String carName,
        UUID configurationId,
        String color,
        Boolean availableForSale,
        Boolean availableForTestDrive
        ) {}