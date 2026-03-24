package ru.pogosian.business.filters;

import ru.pogosian.business.cars.Car;

public interface CarSpecification {
    boolean isSatisfied(Car car);

    default CarSpecification and(CarSpecification other) {
        return car -> this.isSatisfied(car) && other.isSatisfied(car);
    }
}
