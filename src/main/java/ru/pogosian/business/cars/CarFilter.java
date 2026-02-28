package ru.pogosian.business.cars;

import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Builder
public class CarFilter {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Set<Color> color;
    private Set<String> modelBrand;
    private Set<String> modelName;
    private int minHorsePower;
    private int maxHorsePower;
    private double minEngineVolume;
    private double maxEngineVolume;
    private GearboxType gearboxType;
    private DriveType driveType;
    private BodyType bodyType;
    private FuelType fuelType;
}