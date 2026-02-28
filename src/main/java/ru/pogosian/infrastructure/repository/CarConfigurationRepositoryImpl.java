package ru.pogosian.infrastructure.repository;


import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.excrptions.IncompatibleComponentException;
import ru.pogosian.business.repositories.CarConfigurationRepository;

public class CarConfigurationRepositoryImpl implements CarConfigurationRepository {
    private Map<UUID, CarConfiguration> store =  new HashMap<UUID, CarConfiguration>();
    @Override
    public void save(CarConfiguration CarConfiguration) {
        if(CarConfiguration.getUsedDetails().size() < 4)
            throw new DomainValidationException("You need to specify 4 used details");

        for (var carDetail : CarConfiguration.getUsedDetails()) {
            if (carDetail.getCompatibleModelsIds() == null || !carDetail.getCompatibleModelsIds().contains(CarConfiguration.getConfigurationModelId())) {
                throw new IncompatibleComponentException("incompatiable details in config");
            }
        }
        store.put(CarConfiguration.getConfigurationId(), CarConfiguration);
    }

    @Override
    public CarConfiguration findById(UUID id) {
        if(!store.containsKey(id))
            throw new DomainValidationException("ComplectationCarOrder with id " + id + " does not exist");
        CarConfiguration CarConfiguration = store.get(id);
        return CarConfiguration;
    }

    @Override
    public List<CarConfiguration> findAll() {
        return new ArrayList<CarConfiguration>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        if(!store.containsKey(id))
            throw new DomainValidationException("ComplectationCarOrder with id " + id + " does not exist");
        store.remove(id);
    }
}
