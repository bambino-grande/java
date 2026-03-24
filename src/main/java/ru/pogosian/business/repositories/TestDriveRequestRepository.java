package ru.pogosian.business.repositories;

import ru.pogosian.business.testDrive.TestDriveRequest;

import java.util.UUID;
import java.util.List;

public interface TestDriveRequestRepository {
    void save(TestDriveRequest request);
    TestDriveRequest findById(UUID id);
    List<TestDriveRequest> findAll();
    void deleteById(UUID id);
}
