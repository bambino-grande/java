package ru.pogosian.business.repositories;

import org.springframework.data.domain.Pageable;
import ru.pogosian.business.testDrive.TestDriveRequest;

import java.util.UUID;
import java.util.List;

public interface TestDriveRequestRepository {
    void save(TestDriveRequest request);
    TestDriveRequest findById(UUID id);
    List<TestDriveRequest> findAll(Pageable pageable);
    void deleteById(UUID id);
}
