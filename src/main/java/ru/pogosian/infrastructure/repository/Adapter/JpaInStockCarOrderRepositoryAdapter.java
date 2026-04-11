package ru.pogosian.infrastructure.repository.Adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.repositories.InStockCarOrderRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.InStockCarOrder.InStockCarOrderJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaInStockCarOrderRepository;
import ru.pogosian.infrastructure.repository.Mapper.Mapper;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaInStockCarOrderRepositoryAdapter implements InStockCarOrderRepository {
    private JpaInStockCarOrderRepository JpaInStockCarOrderRepository;
    private Mapper mapper;

    @Override
    public void save(InStockCarOrder InStockCarOrder) {
        if(InStockCarOrder ==  null)
            throw new DomainValidationException("Complectation car order is null");
        JpaInStockCarOrderRepository.save(mapper.toJpaEntity(InStockCarOrder));
    }

    @Override
    public InStockCarOrder findById(UUID id) {
        if(!JpaInStockCarOrderRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("InStockCarOrder with id " + id + " does not exist");
        return mapper.toDomain(JpaInStockCarOrderRepository.findByIdAndRemovedFalse(id).orElseThrow());
    }

    @Override
    public List<InStockCarOrder> findAll(Pageable pageable) {
        return JpaInStockCarOrderRepository.findAllByRemovedFalse(pageable).getContent().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        if(!JpaInStockCarOrderRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("InStockCarOrder with id " + id + " does not exist");
        InStockCarOrderJpaEntity InStockCarOrderJpaEntity = JpaInStockCarOrderRepository.findByIdAndRemovedFalse(id).orElseThrow();
        InStockCarOrderJpaEntity.setRemoved(true);
        JpaInStockCarOrderRepository.save(InStockCarOrderJpaEntity);
    }
}
