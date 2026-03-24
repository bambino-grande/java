package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.Car;

import java.math.BigDecimal;

@AllArgsConstructor
public class CarMaxPriceSpecfication implements CarSpecification {
    private final BigDecimal maxPrice;

    @Override
    public boolean isSatisfied(Car car) {
        if(maxPrice !=  null) {
            if (car.getPrice().compareTo(maxPrice) > 0)
                return false;
        }
        return true;
    }
}
