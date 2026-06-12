package ru.pogosian.business.services;

import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.business.detail.CarDetails;

import java.util.Set;
import java.util.UUID;

public interface buildConfigurationService {
    CarConfiguration buildCarConfiguration(UUID modelId, Set<CarDetails> usedDetails);
}