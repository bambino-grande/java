package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.repositories.CarModelRepository;

@AllArgsConstructor
public class CarMinEngineVolumeSpecification implements CarSpecification {
    private final CarModelRepository carModelRepository;
    private final double minEngineVolume;

    @Override
    public boolean isSatisfied(Car car) {
        if (minEngineVolume != 0) {
            if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                    carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getEngineVolume() < minEngineVolume)
                return false;
        }
        return true;
    }
}
