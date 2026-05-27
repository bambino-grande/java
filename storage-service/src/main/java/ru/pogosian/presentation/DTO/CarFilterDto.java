package ru.pogosian.presentation.DTO;

import ru.pogosian.business.cars.*;

import java.math.BigDecimal;
import java.util.Set;

public record CarFilterDto(
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Set<ColorTypes> color,
        Set<String> modelBrand,
        Set<String> modelName,
        int minHorsePower,
        int maxHorsePower,
        double minEngineVolume,
        double maxEngineVolume,
        GearboxType gearboxType,
        DriveType driveType,
        BodyType bodyType,
        FuelType fuelType)
{
}
