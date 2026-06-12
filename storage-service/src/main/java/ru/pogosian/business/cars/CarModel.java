package ru.pogosian.business.cars;

import lombok.Builder;
import lombok.Getter;
import ru.pogosian.business.detail.CarDetails;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.Map;
import java.util.Set;

@Getter
@Builder
public class CarModel {
    private UUID  modelId;
    private String modelBrand;
    private String modelName;
    private BigDecimal basePrice;
    private BodyType bodyType;
    private FuelType fuelType;
    private int horsePower;
    private double engineVolume;
    private GearboxType gearboxType;
    private DriveType driveType;
    private Set<CarDetails> details;
    private Set<CarDetails> availableDetails;
}