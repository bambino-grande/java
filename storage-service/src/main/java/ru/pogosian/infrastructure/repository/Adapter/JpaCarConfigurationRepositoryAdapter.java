package ru.pogosian.infrastructure.repository.Adapter;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.excrptions.IncompatibleComponentException;
import ru.pogosian.business.repositories.CarConfigurationRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.CarConfigurationJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaCarConfigurationRepository;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaCarDetailRepository;
import ru.pogosian.infrastructure.repository.Mapper.CarConfigurationMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaCarConfigurationRepositoryAdapter implements CarConfigurationRepository {
    private final JpaCarConfigurationRepository carConfigurationRepository;
    private final JpaCarDetailRepository carDetailRepository;
    private final CarConfigurationMapper mapper;

    @Override
    public void save(CarConfiguration carConfiguration) {
        if(carConfiguration.getUsedDetails().size() < 4)
            throw new DomainValidationException("You need to specify 4 used details");

        carConfiguration.getUsedDetails().forEach(carDetail -> {
            if (carDetail.getCompatibleModelsIds() == null || !carDetail.getCompatibleModelsIds().contains(carConfiguration.getConfigurationModelId())) {
                throw new IncompatibleComponentException("incompatiable details in config");
            }
        });
        Set<CarDetailJpaEntity> usedDetails = new HashSet<>();

        carConfiguration.getUsedDetails().forEach(carDetail -> {
            usedDetails.add(carDetailRepository.findById(carDetail.getId()).orElseThrow(() -> new DomainValidationException("Car detail with id " + carDetail.getId() + " does not exist")));
        });
        CarConfigurationJpaEntity entity = mapper.toJpaEntity(carConfiguration, usedDetails);
        carConfigurationRepository.save(entity);
    }

    @Override
    public CarConfiguration findById(UUID id) {
        return mapper.toDomain(carConfigurationRepository.findById(id).orElseThrow(() -> new DomainValidationException("ComplectationCarOrder with id " + id + " does not exist")));
    }

    @Override
    public List<CarConfiguration> findAll() {
        return carConfigurationRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        CarConfigurationJpaEntity carConfigurationJpaEntity = carConfigurationRepository.findById(id).orElseThrow(() -> new DomainValidationException("ComplectationCarOrder with id " + id + " does not exist"));
        carConfigurationJpaEntity.setRemoved(true);
        carConfigurationRepository.save(carConfigurationJpaEntity);
    }
}