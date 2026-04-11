package ru.pogosian.presentation.DTO;

import ru.pogosian.business.cars.BodyType;
import ru.pogosian.business.cars.DriveType;
import ru.pogosian.business.cars.FuelType;
import ru.pogosian.business.cars.GearboxType;

import java.math.BigDecimal;
import java.util.Set;

public record CarFilterDto(
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Set<String> color,
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
