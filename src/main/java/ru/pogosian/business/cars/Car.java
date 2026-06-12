package ru.pogosian.business.cars;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Car {
    private UUID carId;
    private String carName;
    private CarConfiguration configuration;
    private ColorTypes color;
    private BigDecimal price;
    public Boolean availableForSale;
    public Boolean availableForTestDrive;
}