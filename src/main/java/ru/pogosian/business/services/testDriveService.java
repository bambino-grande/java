package ru.pogosian.business.services;

import ru.pogosian.business.testDrive.TestDriveRequest;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

public interface testDriveService {
    TestDriveRequest createTestDriveRequest(UUID clientID, UUID carID, LocalDateTime startingTime);

    List<TestDriveRequest> listTestDriveRequests();
    void makeCarAvailableForTestDrive(UUID carID);
    void unmakeCarAvailableForTestDrive(UUID carID);
}
