package ru.pogosian.business.detail.factories;

import ru.pogosian.business.detail.CarDetails;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public interface DetailFactory {
    CarDetails create(String name, Set<UUID> compatibleCarModelsId, BigDecimal deltaPrice);

    CarDetails create(String name, Set<UUID> compatibleCarModelsId, BigDecimal deltaPrice, UUID id);

}
