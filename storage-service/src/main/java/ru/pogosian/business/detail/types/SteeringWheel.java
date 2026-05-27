package ru.pogosian.business.detail.types;

import ru.pogosian.business.detail.CarDetails;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class SteeringWheel extends CarDetails {
    public SteeringWheel(String name, Set<UUID> compatibleCarModelsId, BigDecimal deltaPrice) {
        super(name, compatibleCarModelsId, deltaPrice);
    }

    public SteeringWheel(String name, Set<UUID> compatibleCarModelsId, BigDecimal deltaPrice, UUID id) {
        super(name, compatibleCarModelsId, deltaPrice, id);
    }
}