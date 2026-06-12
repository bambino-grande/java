package ru.pogosian.presentation.DTO.request;

import ru.pogosian.business.detail.CarDetails;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CreateOrUpdateCarConfigurationRequest(
    UUID configurationModelId,
    BigDecimal totalPrice,
    Set<CreateOrUpdateCarDetailRequest> usedDetails
){}