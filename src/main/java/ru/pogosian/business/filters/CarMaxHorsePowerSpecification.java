package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.repositories.CarModelRepository;

@AllArgsConstructor
public class CarMaxHorsePowerSpecification implements CarSpecification{
    private final int maxHorsePower;
    private final CarModelRepository carModelRepository;

    @Override
    public boolean isSatisfied(Car car) {
        if(maxHorsePower != 0) {
            if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                    carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getHorsePower() > maxHorsePower)
                return false;
        }
        return true;
    }
}
