package ru.pogosian.infrastructure.repository.Adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.InStockCarOrderRepository;
import ru.pogosian.business.repositories.TestDriveRequestRepository;
import ru.pogosian.business.testDrive.TestDriveRequest;
import ru.pogosian.infrastructure.repository.JpaEntity.TestDriveRequestJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaTestDriveRequestRepository;
import ru.pogosian.infrastructure.repository.Mapper.InStockCarOrderMapper;
import ru.pogosian.infrastructure.repository.Mapper.TestDriveRequestMapper;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaTestDriveRequestRepositoryAdapter implements TestDriveRequestRepository {
    private JpaTestDriveRequestRepository JpaTestDriveRequestRepository;
    private TestDriveRequestMapper mapper;

    @Override
    public void save(TestDriveRequest TestDriveRequest) {
        if(TestDriveRequest ==  null)
            throw new DomainValidationException("Complectation car order is null");
        JpaTestDriveRequestRepository.save(mapper.toJpaEntity(TestDriveRequest));
    }
     @Override
    public TestDriveRequest findById(UUID id) {
        return mapper.toDomain(JpaTestDriveRequestRepository.findById(id).orElseThrow(() -> new DomainValidationException("TestDriveRequest with id " + id + " does not exist")));
    }

    @Override
    public List<TestDriveRequest> findAll(Pageable pageable) {
        return JpaTestDriveRequestRepository.findAll(pageable).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        TestDriveRequestJpaEntity TestDriveRequestJpaEntity = JpaTestDriveRequestRepository.findById(id).orElseThrow(() -> new DomainValidationException("TestDriveRequest with id " + id + " does not exist"));
        TestDriveRequestJpaEntity.setRemoved(true);
        JpaTestDriveRequestRepository.save(TestDriveRequestJpaEntity);
    }
}
