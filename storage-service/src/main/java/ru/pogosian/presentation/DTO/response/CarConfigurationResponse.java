package ru.pogosian.presentation.DTO.response;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CarConfigurationResponse(
        UUID carConfigurationId,
        BigDecimal totalPrice,
        UUID configurationModelId,
        Set<CarDetailResponse> usedDetails
) {
}
