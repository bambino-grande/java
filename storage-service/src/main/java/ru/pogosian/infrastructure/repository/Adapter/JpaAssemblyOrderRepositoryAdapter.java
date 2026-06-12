package ru.pogosian.infrastructure.repository.Adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.assembly.AssemblyOrder;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.AssemblyOrderRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.AssemblyOrderJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaAssemblyOrderRepository;
import ru.pogosian.infrastructure.repository.Mapper.AssemblyOrderMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaAssemblyOrderRepositoryAdapter implements AssemblyOrderRepository {
    private final JpaAssemblyOrderRepository jpaAssemblyOrderRepository;
    private final AssemblyOrderMapper assemblyOrderMapper;

    @Override
    public void save(AssemblyOrder assemblyOrder) {
        if(assemblyOrder == null)
            throw new DomainValidationException("AssemblyOrder is null");

        AssemblyOrderJpaEntity entity = assemblyOrderMapper.toJpaEntity(assemblyOrder);
        jpaAssemblyOrderRepository.save(entity);
    }

    @Override
    public AssemblyOrder findById(UUID id) {
        return assemblyOrderMapper.toDomain(jpaAssemblyOrderRepository.findById(id).orElseThrow(() -> new DomainValidationException("AssemblyOrder not found")));
    }

    @Override
    public List<AssemblyOrder> findAll(Pageable pageable) {
        return jpaAssemblyOrderRepository.findAll(pageable).getContent().stream().map(assemblyOrderMapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        AssemblyOrderJpaEntity jpaEntity = jpaAssemblyOrderRepository.findById(id).orElse(null);
        jpaEntity.setRemoved(true);
        jpaAssemblyOrderRepository.save(jpaEntity);
    }
}
