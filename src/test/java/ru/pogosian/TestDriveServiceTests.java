package ru.pogosian;

import org.junit.jupiter.api.Test;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.business.cars.ColorTypes;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.CarRepository;
import ru.pogosian.business.repositories.TestDriveRequestRepository;
import ru.pogosian.business.testDrive.TestDriveRequest;
import ru.pogosian.business.services.testDriveServiceImpl;
import ru.pogosian.security.SecurityService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDriveServiceTests {
    @Test
    void shouldCreateTestDriveAndRequestSuccessfully() {
        CarRepository usingCarRepository = mock(CarRepository.class);
        TestDriveRequestRepository usingRequestRepository = mock(TestDriveRequestRepository.class);
        SecurityService securityService = mock(SecurityService.class);

        UUID carId = UUID.randomUUID();
        UUID modelId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();

        CarConfiguration configuration = CarConfiguration.builder()
                .configurationId(UUID.randomUUID())
                .configurationModelId(modelId)
                .totalPrice(BigDecimal.valueOf(10000))
                .usedDetails(Set.of())
                .build();

        Car car = Car.builder()
                .carId(carId)
                .carName("vishnewauya 7")
                .configuration(configuration)
                .price(BigDecimal.valueOf(10000))
                .color(ColorTypes.BLUE)
                .availableForTestDrive(true)
                .availableForSale(true)
                .build();

        when(usingCarRepository.findById(carId)).thenReturn(car);

        testDriveServiceImpl testDriveService = new testDriveServiceImpl(usingCarRepository, usingRequestRepository, securityService);
        LocalDateTime date = LocalDateTime.now();
        TestDriveRequest request = testDriveService.createTestDriveRequest(clientId, car.getCarId(), date);

        assertEquals(clientId, request.getClientId());
        assertEquals(carId, request.getCarId());
    }

    @Test
    void shouldThrowWhenCarNotAllowedForTestDrive() {
        CarRepository usingCarRepository = mock(CarRepository.class);
        TestDriveRequestRepository usingRequestRepository = mock(TestDriveRequestRepository.class);
        SecurityService securityService = mock(SecurityService.class);

        UUID carId = UUID.randomUUID();
        UUID modelId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();

        CarConfiguration configuration = CarConfiguration.builder()
                .configurationId(UUID.randomUUID())
                .configurationModelId(modelId)
                .totalPrice(BigDecimal.valueOf(10000))
                .usedDetails(Set.of())
                .build();

        Car car = Car.builder()
                .carId(carId)
                .carName("vishnewauya 7")
                .configuration(configuration)
                .price(BigDecimal.valueOf(10000))
                .color(ColorTypes.BLUE)
                .availableForTestDrive(false)
                .availableForSale(true)
                .build();

        when(usingCarRepository.findById(carId)).thenReturn(car);

        testDriveServiceImpl testDriveService = new testDriveServiceImpl(usingCarRepository, usingRequestRepository, securityService);
        assertThrows(DomainValidationException.class, () -> testDriveService.createTestDriveRequest(clientId, carId, LocalDateTime.now()));
    }
}
