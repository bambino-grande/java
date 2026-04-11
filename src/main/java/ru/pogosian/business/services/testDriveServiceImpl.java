package ru.pogosian.business.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.CarRepository;
import ru.pogosian.business.repositories.TestDriveRequestRepository;
import ru.pogosian.business.testDrive.TestDriveRequest;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class testDriveServiceImpl implements TestDriveService {
    private final CarRepository usingCarRepository;
    private final TestDriveRequestRepository usingTestDriveRequestRepository;

    @Transactional
    @Override
    public TestDriveRequest createTestDriveRequest(UUID clientID, UUID carID, LocalDateTime startingTime) {
        Car car = usingCarRepository.findById(carID);

        if(car.getAvailableForTestDrive()==false) {
            throw new DomainValidationException("car is not allowed to drive");
        }

        TestDriveRequest request = TestDriveRequest.builder()
                .testDriveId(UUID.randomUUID())
                .isCarCapableForTestDrive(car.getAvailableForTestDrive())
                .clientId(clientID)
                .carId(carID)
                .testDriveStartAt(startingTime)
                .modelId(car.getConfiguration().getConfigurationModelId())
                .build();

        usingTestDriveRequestRepository.save(request);
        return request;
    }
    @Override
    public List<TestDriveRequest> listTestDriveRequests(Pageable pageable) {
        return usingTestDriveRequestRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void makeCarAvailableForTestDrive(UUID carID) {
        Car car = usingCarRepository.findById(carID);
        car.availableForTestDrive = true;
        usingCarRepository.save(car);
    }

    @Transactional
    @Override
    public void unmakeCarAvailableForTestDrive(UUID carID) {
        Car car = usingCarRepository.findById(carID);
        car.availableForTestDrive = false;
        usingCarRepository.save(car);
    }
}
