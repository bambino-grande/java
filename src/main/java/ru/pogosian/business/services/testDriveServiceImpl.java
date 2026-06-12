package ru.pogosian.business.services;

import lombok.RequiredArgsConstructor;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.CarRepository;
import ru.pogosian.business.repositories.TestDriveRequestRepository;
import ru.pogosian.business.testDrive.TestDriveRequest;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class testDriveServiceImpl implements testDriveService {
    private final CarRepository usingCarRepository;
    private final TestDriveRequestRepository usingTestDriveRequestRepository;

    @Override
    public TestDriveRequest createTestDriveRequest(UUID clientID, UUID carID, LocalDateTime startingTime) {
        Car car = usingCarRepository.findById(carID);

        if(car.getAvailableForTestDrive()==false) {
            throw new DomainValidationException("car is not allowed to drive");
        }

        TestDriveRequest request = TestDriveRequest.builder()
                .isCarCapableForTestDrive(car.getAvailableForTestDrive())
                .clientId(clientID)
                .cartId(carID)
                .testDriveStartAt(startingTime)
                .build();

        usingTestDriveRequestRepository.save(request);
        return request;
    }
    @Override
    public List<TestDriveRequest> listTestDriveRequests() {
        return usingTestDriveRequestRepository.findAll();
    }

    @Override
    public void makeCarAvailableForTestDrive(UUID carID) {
        Car car = usingCarRepository.findById(carID);
        car.availableForTestDrive = true;
    }

    @Override
    public void unmakeCarAvailableForTestDrive(UUID carID) {
        Car car = usingCarRepository.findById(carID);
        car.availableForTestDrive = false;
    }
}
