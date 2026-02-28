package ru.pogosian.infrastructure.repository;


import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.pogosian.business.cars.CarModel;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.CarModelRepository;

public class CarModelRepositoryImpl implements CarModelRepository {
    private Map<UUID, CarModel> store =  new HashMap <UUID, CarModel>();
    @Override
    public void save(CarModel carModel) {
        if(carModel == null)
            throw new DomainValidationException("CarModel cannot be null");
        store.put(carModel.getModelId(), carModel);
    }

    @Override
    public CarModel findById(UUID id) {
        if(!store.containsKey(id))
            throw new DomainValidationException("Car with id " + id + " does not exist");
        CarModel carModel = store.get(id);
        return carModel;
    }

    @Override
    public List<CarModel> findAll() {
        return new ArrayList<CarModel>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        if(!store.containsKey(id))
            throw new DomainValidationException("Car with id " + id + " does not exist");
        store.remove(id);
    }
}
