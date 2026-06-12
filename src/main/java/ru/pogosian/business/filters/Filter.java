package ru.pogosian.business.filters;

import ru.pogosian.business.cars.BodyType;
import ru.pogosian.business.cars.DriveType;
import ru.pogosian.business.cars.FuelType;
import ru.pogosian.business.cars.GearboxType;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Set;

public class Filter {
    public record CarFilter(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Set<Color> color,
            Set<String> modelBrand,
            Set<String> modelName,
            int minHorsePower,
            int maxHorsePower,
            double minEngineVolume,
            double maxEngineVolume,
            GearboxType gearboxType,
            DriveType driveType,
            BodyType bodyType,
            FuelType fuelType
    ){}
}
