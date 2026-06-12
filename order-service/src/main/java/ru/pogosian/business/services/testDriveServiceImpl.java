package ru.pogosian.business.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.TestDriveRequestRepository;
import ru.pogosian.business.testDrive.TestDriveRequest;
import ru.pogosian.business.users.Manager;
import ru.pogosian.business.users.SystemAdmin;
import ru.pogosian.business.users.User;
import ru.pogosian.infrastructure.client.StorageCarClient;
import ru.pogosian.security.SecurityService;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class testDriveServiceImpl implements TestDriveService {
    private final StorageCarClient storageCarClient;
    private final TestDriveRequestRepository usingTestDriveRequestRepository;
    private final SecurityService securityService;

    @Transactional
    @Override
    public TestDriveRequest createTestDriveRequest(UUID clientID, UUID carID, LocalDateTime startingTime) {
        StorageCarClient.CarInstance car = storageCarClient.getCar(carID);

        if(car.availableForTestDrive()==false) {
            throw new DomainValidationException("car is not allowed to drive");
        }

        TestDriveRequest request = TestDriveRequest.builder()
                .testDriveId(UUID.randomUUID())
                .isCarCapableForTestDrive(true)
                .clientId(clientID)
                .carId(carID)
                .testDriveStartAt(startingTime)
                .modelId(car.configurationModelId())
                .build();

        usingTestDriveRequestRepository.save(request);
        return request;
    }
    @Override
    public List<TestDriveRequest> listTestDriveRequests(Pageable pageable) {
        User currnetUser = securityService.getCurrentUser();
        if(currnetUser instanceof Manager || currnetUser instanceof SystemAdmin)
            return  usingTestDriveRequestRepository.findAll(pageable);
        else
            return usingTestDriveRequestRepository.findAllByClientId(currnetUser.getId(), pageable);
    }

    @Transactional
    @Override
    public void makeCarAvailableForTestDrive(UUID carID) {
        storageCarClient.makeCarAvailableForTestDrive(carID, true);
    }

    @Transactional
    @Override
    public void unmakeCarAvailableForTestDrive(UUID carID) {
        storageCarClient.makeCarAvailableForTestDrive(carID, false);
    }
}
