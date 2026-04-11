package ru.pogosian.infrastructure.repository.Adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.CarDetailsRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.CarConfigurationJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaCarDetailRepository;
import ru.pogosian.infrastructure.repository.Mapper.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaCarDetailRepositoryAdapter implements CarDetailsRepository {
    Mapper mapper;
    private JpaCarDetailRepository jpaCarDetailRepository;

    @Override
    public void save(CarDetails carDetail) {
        if(carDetail == null)
            throw new DomainValidationException("Car details cannot be null");
        jpaCarDetailRepository.save(mapper.toJpaEntity(carDetail));
    }

    @Override
    public CarDetails findById(UUID id) {
        if(!jpaCarDetailRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("Car detail with id " + id + " does not exist");
        return mapper.toDomain(jpaCarDetailRepository.findByIdAndRemovedFalse(id).orElseThrow());
    }

    @Override
    public List<CarDetails> findAll(Pageable pageable) {
        return jpaCarDetailRepository.findAllByRemovedFalse(pageable).getContent().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        if(!jpaCarDetailRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("Car detail with id " + id + " does not exist");
        CarDetailJpaEntity CarDetailJpaEntity = jpaCarDetailRepository.findByIdAndRemovedFalse(id).orElseThrow();
        CarDetailJpaEntity.setRemoved(true);
        jpaCarDetailRepository.save(CarDetailJpaEntity);
    }
}