package ru.pogosian.presentation.DTO.response;

import ru.pogosian.business.cars.ColorTypes;

import java.math.BigDecimal;
import java.util.UUID;

public record CarResponse(
    UUID carId,
    String carName,
    CarConfigurationResponse configuration,
    ColorTypes color,
    BigDecimal price,
    Boolean availableForSale,
    Boolean availableForTestDrive
    ){}