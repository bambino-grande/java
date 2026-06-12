package ru.pogosian.business.cars;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Car {
    private UUID carId;
    private String carName;
    private CarConfiguration configuration;
    private ColorTypes color;
    private BigDecimal price;
    private Boolean availableForSale;
    @Setter
    private Boolean availableForTestDrive;
}