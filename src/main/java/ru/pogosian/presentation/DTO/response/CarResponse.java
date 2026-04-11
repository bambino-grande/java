package ru.pogosian.presentation.DTO.response;

import ru.pogosian.presentation.DTO.request.CreateOrUpdateCarConfigurationRequest;

import java.awt.*;
import java.math.BigDecimal;
import java.util.UUID;

public record CarResponse(
    UUID carId,
    String carName,
    CarConfigurationResponse configuration,
    String color,
    BigDecimal price,
    Boolean availableForSale,
    Boolean availableForTestDrive
    ){}