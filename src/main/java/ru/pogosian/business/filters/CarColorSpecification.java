package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.Car;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
public class CarColorSpecification implements CarSpecification {
    private final Set<Color> color;

    @Override
    public boolean isSatisfied(Car car) {
        if (color != null){
            if(!color.contains(car.getColor()))
                return  false;
        }
        return true;
    }
}