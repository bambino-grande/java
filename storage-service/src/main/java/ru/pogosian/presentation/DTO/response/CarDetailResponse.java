package ru.pogosian.presentation.DTO.response;

import ru.pogosian.presentation.DTO.Types.CarDetailTypes;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CarDetailResponse(
    String name,
    CarDetailTypes carDetailTypes,
    BigDecimal deltaPrice,
    Set<UUID> compatibleModelsIds,
    UUID id
) {
}
