package ru.pogosian.presentation.DTO.request;

import ru.pogosian.business.cars.BodyType;
import ru.pogosian.business.cars.DriveType;
import ru.pogosian.business.cars.FuelType;
import ru.pogosian.business.cars.GearboxType;

import java.math.BigDecimal;
import java.util.Set;

public record CreateOrUpdateCarModelRequest(
    String modelBrand,
    String modelName,
    BigDecimal basePrice,
    BodyType bodyType,
    FuelType fuelType,
    int horsePower,
    double engineVolume,
    GearboxType gearboxType,
    DriveType driveType,
    Set<CreateOrUpdateCarDetailRequest>details,
    Set<CreateOrUpdateCarDetailRequest> availableDetails
) {
}
