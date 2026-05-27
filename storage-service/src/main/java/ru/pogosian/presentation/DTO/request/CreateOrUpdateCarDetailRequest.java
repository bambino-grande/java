package ru.pogosian.presentation.DTO.request;

import ru.pogosian.presentation.DTO.Types.CarDetailTypes;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CreateOrUpdateCarDetailRequest(
    String name,
    CarDetailTypes carDetailTypes,
    BigDecimal deltaPrice,
    Set<UUID> compatibleModelsIds
){}