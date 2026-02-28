package ru.pogosian;

import org.junit.jupiter.api.Test;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.cars.CarModel;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.excrptions.IncompatibleComponentException;
import ru.pogosian.business.repositories.CarDetailsRepository;
import ru.pogosian.business.repositories.CarModelRepository;
import ru.pogosian.business.repositories.CarRepository;
import ru.pogosian.business.repositories.TestDriveRequestRepository;
import ru.pogosian.business.services.CarService;
import ru.pogosian.business.services.CarServiceImpl;
import ru.pogosian.business.testDrive.TestDriveRequest;
import ru.pogosian.infrastructure.repository.CarDetailsRepositoryImpl;
import ru.pogosian.infrastructure.repository.CarModelRepositoryImpl;
import ru.pogosian.infrastructure.repository.CarRepositoryImpl;
import ru.pogosian.infrastructure.repository.TestDriveRequestRepositoryImpl;
import ru.pogosian.business.services.testDriveServiceImpl;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDriveServiceTests {
    @Test
    void shouldCreateTestDriveAndRequestSuccessfully(){
        CarModelRepository usingCarModelRepository = new CarModelRepositoryImpl();
        CarDetailsRepository usingCarDetailsRepository = new CarDetailsRepositoryImpl();
        CarRepository usingCarRepository = new CarRepositoryImpl();
        TestDriveRequestRepository usingRequestRepository = new TestDriveRequestRepositoryImpl();

        CarService carService = new CarServiceImpl(usingCarDetailsRepository, usingCarRepository, usingCarModelRepository);

        UUID modelId = UUID.randomUUID();
        CarModel model = CarModel.builder()
                .modelId(modelId)
                .modelBrand("jiga")
                .modelName("4445")
                .basePrice(BigDecimal.valueOf(10000))
                .build();

        usingCarModelRepository.save(model);

        Car car = carService.CreateCarFromModel(modelId, "vishnewauya 7", Color.GREEN, true, true);
        usingCarRepository.save(car);

        UUID clientId = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();

        testDriveServiceImpl testDriveService = new testDriveServiceImpl(usingCarRepository, usingRequestRepository);
        TestDriveRequest request = testDriveService.createTestDriveRequest(clientId, car.getCarId(), date);

        assertEquals(clientId, request.getClientId());
        assertEquals(car.getCarId(), request.getCartId());
    }
    @Test
    void shouldThrowWhenCarNotAllowedForTestDrive(){
        CarModelRepository usingCarModelRepository = new CarModelRepositoryImpl();
        CarDetailsRepository usingCarDetailsRepository = new CarDetailsRepositoryImpl();
        CarRepository usingCarRepository = new CarRepositoryImpl();
        TestDriveRequestRepository usingRequestRepository = new TestDriveRequestRepositoryImpl();

        CarService carService = new CarServiceImpl(usingCarDetailsRepository, usingCarRepository, usingCarModelRepository);

        UUID modelId = UUID.randomUUID();
        CarModel model = CarModel.builder()
                .modelId(modelId)
                .modelBrand("jiga")
                .modelName("4445")
                .basePrice(BigDecimal.valueOf(10000))
                .build();

        usingCarModelRepository.save(model);

        Car car = carService.CreateCarFromModel(modelId, "vishnewauya 7", Color.GREEN, true, false);
        usingCarRepository.save(car);

        UUID clientId = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();

        testDriveServiceImpl testDriveService = new testDriveServiceImpl(usingCarRepository, usingRequestRepository);

        assertThrows(DomainValidationException.class, () -> testDriveService.createTestDriveRequest(clientId, car.getCarId(), date));
    }
}
