package ru.pogosian.infrastructure.repository.Adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.cars.CarModel;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.CarModelRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.CarModelJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaCarModelRepository;
import ru.pogosian.infrastructure.repository.Mapper.Mapper;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaCarModelRepositoryAdapter implements CarModelRepository {
    Mapper mapper;
    private JpaCarModelRepository jpaCarModelRepository;

    @Override
    public void save(CarModel carModel) {
        if(carModel == null)
            throw new DomainValidationException("Car details cannot be null");
        jpaCarModelRepository.save(mapper.toJpaEntity(carModel));
    }

    @Override
    public CarModel findById(UUID id) {
        if(!jpaCarModelRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("Car model with id " + id + " does not exist");
        return mapper.toDomain(jpaCarModelRepository.findByIdAndRemovedFalse(id).orElseThrow());
    }

    @Override
    public List<CarModel> findAll() {
        return jpaCarModelRepository.findAllByRemovedFalse().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        if(!jpaCarModelRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("Car model with id " + id + " does not exist");
        CarModelJpaEntity CarModelJpaEntity = jpaCarModelRepository.findByIdAndRemovedFalse(id).orElseThrow();
        CarModelJpaEntity.setRemoved(true);
        jpaCarModelRepository.save(CarModelJpaEntity);
    }
}
