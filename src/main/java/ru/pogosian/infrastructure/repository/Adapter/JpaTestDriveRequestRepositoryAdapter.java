package ru.pogosian.infrastructure.repository.Adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.TestDriveRequestRepository;
import ru.pogosian.business.testDrive.TestDriveRequest;
import ru.pogosian.infrastructure.repository.JpaEntity.InStockCarOrder.InStockCarOrderJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.TestDriveRequestJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaTestDriveRequestRepository;
import ru.pogosian.infrastructure.repository.Mapper.Mapper;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaTestDriveRequestRepositoryAdapter implements TestDriveRequestRepository {
    private JpaTestDriveRequestRepository JpaTestDriveRequestRepository;
    private Mapper mapper;

    @Override
    public void save(TestDriveRequest TestDriveRequest) {
        if(TestDriveRequest ==  null)
            throw new DomainValidationException("Complectation car order is null");
        JpaTestDriveRequestRepository.save(mapper.toJpaEntity(TestDriveRequest));
    }
     @Override
    public TestDriveRequest findById(UUID id) {
        if(!JpaTestDriveRequestRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("TestDriveRequest with id " + id + " does not exist");
        return mapper.toDomain(JpaTestDriveRequestRepository.findByIdAndRemovedFalse(id).orElseThrow());
    }

    @Override
    public List<TestDriveRequest> findAll(Pageable pageable) {
        return JpaTestDriveRequestRepository.findAllByRemovedFalse(pageable).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        if(!JpaTestDriveRequestRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("TestDriveRequest with id " + id + " does not exist");
        TestDriveRequestJpaEntity TestDriveRequestJpaEntity = JpaTestDriveRequestRepository.findByIdAndRemovedFalse(id).orElseThrow();
        TestDriveRequestJpaEntity.setRemoved(true);
        JpaTestDriveRequestRepository.save(TestDriveRequestJpaEntity);
    }
}
