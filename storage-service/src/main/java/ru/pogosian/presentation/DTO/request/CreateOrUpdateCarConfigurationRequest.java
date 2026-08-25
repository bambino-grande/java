package ru.pogosian.presentation.DTO.request;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CreateOrUpdateCarConfigurationRequest(
    UUID configurationModelId,
    BigDecimal totalPrice,
    Set<CreateOrUpdateCarDetailRequest> usedDetails
){}