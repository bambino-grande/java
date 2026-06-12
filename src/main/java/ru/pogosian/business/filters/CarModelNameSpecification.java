package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.repositories.CarModelRepository;

import java.util.Set;

@AllArgsConstructor
public class CarModelNameSpecification implements CarSpecification {
    private final Set<String> modelNames;
    private final CarModelRepository carModelRepository;

    @Override
    public boolean isSatisfied(Car car) {
        if(modelNames != null) {
            if (!modelNames.contains(carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getModelName()))
                return false;
        }
        return true;
    }
}