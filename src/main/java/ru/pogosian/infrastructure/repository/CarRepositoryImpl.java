package ru.pogosian.infrastructure.repository;

import ru.pogosian.business.cars.Car;
import ru.pogosian.business.repositories.CarRepository;

import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarRepositoryImpl implements CarRepository {
    private Map<UUID, Car> store =  new HashMap<UUID, Car>();
    @Override
    public void save(Car Car) {
        store.put(Car.getCarId(), Car);
    }

    @Override
    public Car findById(UUID id) {
        Car Car = store.get(id);
        return Car;
    }

    @Override
    public List<Car> findAll() {
        return new ArrayList<Car>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }
}
