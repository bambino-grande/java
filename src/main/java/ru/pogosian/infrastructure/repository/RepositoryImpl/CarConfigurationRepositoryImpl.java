package ru.pogosian.infrastructure.repository.RepositoryImpl;


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
    public void save(CarConfiguration carConfiguration) {
        if(carConfiguration.getUsedDetails().size() < 4)
            throw new DomainValidationException("You need to specify 4 used details");

        carConfiguration.getUsedDetails().stream().forEach(carDetail -> {
            if (carDetail.getCompatibleModelsIds() == null || !carDetail.getCompatibleModelsIds().contains(carConfiguration.getConfigurationModelId())) {
                throw new IncompatibleComponentException("incompatiable details in config");
            }
        });
        store.put(carConfiguration.getConfigurationId(), carConfiguration);
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
