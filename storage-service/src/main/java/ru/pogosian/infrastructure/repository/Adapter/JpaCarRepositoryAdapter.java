package ru.pogosian.infrastructure.repository.Adapter;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.filters.Filter;
import ru.pogosian.business.repositories.CarRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.CarJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaCarRepository;
import ru.pogosian.infrastructure.repository.Mapper.CarMapper;
import ru.pogosian.infrastructure.repository.Specification.CarJpaSpecifications;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaCarRepositoryAdapter implements CarRepository {
    private JpaCarRepository jpaCarRepository;
    CarMapper mapper;

    @Override
    public void save(Car car) {
        if(car ==  null)
            throw new DomainValidationException("Car is null");
        jpaCarRepository.save(mapper.toJpaEntity(car));
    }

    @Override
    public Car findById(UUID id) {
        return mapper.toDomain(jpaCarRepository.findById(id).orElseThrow(() -> new DomainValidationException("Car with id " + id + " does not exist")));
    }

    @Override
    public List<Car> findAll() {
        return jpaCarRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        CarJpaEntity CarJpaEntity = jpaCarRepository.findById(id).orElseThrow(() -> new DomainValidationException("Car with id " + id + " does not exist"));
        CarJpaEntity.setRemoved(true);
        jpaCarRepository.save(CarJpaEntity);
    }

    @Override
    public List<Car> findAllByFilter(Filter.CarFilter filter) {
        return jpaCarRepository.findAll(CarJpaSpecifications.byFilter(filter)).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
