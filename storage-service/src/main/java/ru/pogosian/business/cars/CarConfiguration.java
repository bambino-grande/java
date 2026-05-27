package ru.pogosian.business.cars;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import ru.pogosian.business.detail.CarDetails;

@Getter
@Builder
public class CarConfiguration {
    private UUID configurationModelId;
    private UUID configurationId;
    private BigDecimal totalPrice;
    private Set<CarDetails> usedDetails;
}