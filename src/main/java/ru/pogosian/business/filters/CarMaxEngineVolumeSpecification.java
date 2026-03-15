package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.repositories.CarModelRepository;

@AllArgsConstructor
public class CarMaxEngineVolumeSpecification implements CarSpecification {
    private final CarModelRepository carModelRepository;
    private final double maxEngineVolume;

    @Override
    public boolean isSatisfied(Car car) {
        if (maxEngineVolume != 0) {
            if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                    carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getEngineVolume() > maxEngineVolume)
                return false;
        }
        return true;
    }
}
