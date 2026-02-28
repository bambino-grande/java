package ru.pogosian.infrastructure.repository;

import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.CarDetailsRepository;

public class CarDetailsRepositoryImpl implements CarDetailsRepository {
    private Map<UUID, CarDetails> store =  new HashMap<UUID, CarDetails>();

    @Override
    public void save(CarDetails carDetail) {
        if(carDetail == null)
            throw new DomainValidationException("Car details cannot be null");
        store.put(carDetail.getId(), carDetail);
    }

    @Override
    public CarDetails findById(UUID id) {
        if(!store.containsKey(id))
            throw new DomainValidationException("Car detail with id " + id + " does not exist");
        CarDetails CarDetail = store.get(id);
        return CarDetail;
    }

    @Override
    public List<CarDetails> findAll() {
        return new ArrayList<CarDetails>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        if(!store.containsKey(id))
            throw new DomainValidationException("Car detail with id " + id + " does not exist");
        store.remove(id);
    }
}
