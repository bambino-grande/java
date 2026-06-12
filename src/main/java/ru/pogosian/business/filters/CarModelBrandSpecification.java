package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.repositories.CarModelRepository;

import java.util.Set;

@AllArgsConstructor
public class CarModelBrandSpecification implements CarSpecification
{
    private final Set<String> modelBrand;
    private final CarModelRepository carModelRepository;

    @Override
    public boolean isSatisfied(Car car) {
        if(modelBrand != null){
            if(!modelBrand.contains(carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getModelBrand()))
                return false;
        }
        return  true;
    }
}
