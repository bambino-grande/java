package ru.pogosian.infrastructure.repository.Adapter;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.excrptions.IncompatibleComponentException;
import ru.pogosian.business.repositories.CarConfigurationRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.CarConfigurationJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.CarJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaCarConfigurationRepository;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaCarDetailRepository;
import ru.pogosian.infrastructure.repository.Mapper.Mapper;

import java.util.*;

@Repository
@AllArgsConstructor
public class JpaCarConfigurationRepositoryAdapter implements CarConfigurationRepository {
    private final JpaCarConfigurationRepository carConfigurationRepository;
    private final JpaCarDetailRepository carDetailRepository;
    private final Mapper mapper;

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
            usedDetails.add(carDetailRepository.findByIdAndRemovedFalse(carDetail.getId()).orElseThrow());
        });
        carConfigurationRepository.save(mapper.toJpaEntity(carConfiguration));
    }

    @Override
    public CarConfiguration findById(UUID id) {
        if(!carConfigurationRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("ComplectationCarOrder with id " + id + " does not exist");
        return mapper.toDomain(carConfigurationRepository.findByIdAndRemovedFalse(id).orElseThrow());
    }

    @Override
    public List<CarConfiguration> findAll() {
        return carConfigurationRepository.findAllByRemovedFalse().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        if(!carConfigurationRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("ComplectationCarOrder with id " + id + " does not exist");
        CarConfigurationJpaEntity carConfigurationJpaEntity = carConfigurationRepository.findByIdAndRemovedFalse(id).orElseThrow();
        carConfigurationJpaEntity.setRemoved(true);
        carConfigurationRepository.save(carConfigurationJpaEntity);
    }
}