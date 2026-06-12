package ru.pogosian.presentation.DTO.response;

import ru.pogosian.business.cars.BodyType;
import ru.pogosian.business.cars.DriveType;
import ru.pogosian.business.cars.FuelType;
import ru.pogosian.business.cars.GearboxType;
import ru.pogosian.business.detail.CarDetails;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CarModelResponse (
    UUID modelId,
    String modelBrand,
    String modelName,
    BigDecimal basePrice,
    BodyType bodyType,
    FuelType fuelType,
    int horsePower,
    double engineVolume,
    GearboxType gearboxType,
    DriveType driveType,
    Set<CarDetailResponse> details,
    Set<CarDetailResponse> availableDetails
){}