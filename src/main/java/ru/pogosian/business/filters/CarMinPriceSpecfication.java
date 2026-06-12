package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.Car;

import java.math.BigDecimal;

@AllArgsConstructor
public class CarMinPriceSpecfication implements CarSpecification {
    private final BigDecimal minPrice;

    @Override
    public boolean isSatisfied(Car car) {
        if(minPrice !=null) {
            if (car.getPrice().compareTo(minPrice) < 0)
                return false;
        }
        return true;
    }
}