package ru.pogosian.infrastructure.repository.Adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.repositories.ComplectationCarOrderRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.ComplectationCarOrder.ComplectationCarOrderJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaCarRepository;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaComplectationCarOrderRepository;
import ru.pogosian.infrastructure.repository.Mapper.Mapper;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaComplectationCarOrderRepositoryAdapter implements ComplectationCarOrderRepository {
    private JpaComplectationCarOrderRepository jpaComplectationCarOrderRepository;
    private Mapper mapper;

    @Override
    public void save(ComplectationCarOrder ComplectationCarOrder) {
        if(ComplectationCarOrder ==  null)
            throw new DomainValidationException("Complectation car order is null");
        jpaComplectationCarOrderRepository.save(mapper.toJpaEntity(ComplectationCarOrder));
    }

    @Override
    public ComplectationCarOrder findById(UUID id) {
        if(!jpaComplectationCarOrderRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("ComplectationCarOrder with id " + id + " does not exist");
        return mapper.toDomain(jpaComplectationCarOrderRepository.findByIdAndRemovedFalse(id).orElseThrow());
    }

    @Override
    public List<ComplectationCarOrder> findAll(Pageable pageable) {
        return jpaComplectationCarOrderRepository.findAllByRemovedFalse(pageable).getContent().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        if(!jpaComplectationCarOrderRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("ComplectationCarOrder with id " + id + " does not exist");
        ComplectationCarOrderJpaEntity ComplectationCarOrderJpaEntity = jpaComplectationCarOrderRepository.findByIdAndRemovedFalse(id).orElseThrow();
        ComplectationCarOrderJpaEntity.setRemoved(true);
        jpaComplectationCarOrderRepository.save(ComplectationCarOrderJpaEntity);
    }
}
