package ru.pogosian.business.services;

import org.springframework.data.domain.Pageable;
import ru.pogosian.business.testDrive.TestDriveRequest;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

public interface TestDriveService {
    TestDriveRequest createTestDriveRequest(UUID clientID, UUID carID, LocalDateTime startingTime);
    List<TestDriveRequest> listTestDriveRequests(Pageable pageable);
    void makeCarAvailableForTestDrive(UUID carID);
    void unmakeCarAvailableForTestDrive(UUID carID);
}
