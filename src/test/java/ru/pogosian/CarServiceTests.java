package ru.pogosian;

import org.junit.jupiter.api.Test;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.business.cars.CarModel;
import ru.pogosian.business.repositories.CarDetailsRepository;
import ru.pogosian.business.repositories.CarModelRepository;
import ru.pogosian.business.repositories.CarRepository;
import ru.pogosian.business.services.CarService;
import ru.pogosian.business.services.CarServiceImpl;
import ru.pogosian.infrastructure.repository.CarDetailsRepositoryImpl;
import ru.pogosian.infrastructure.repository.CarModelRepositoryImpl;
import ru.pogosian.infrastructure.repository.CarRepositoryImpl;

import java.awt.*;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarServiceTests {
    @Test
    void shouldAddCarAndViewCar(){
        CarModelRepository usingCarModelRepository = new CarModelRepositoryImpl();
        CarDetailsRepository usingCarDetailsRepository = new CarDetailsRepositoryImpl();
        CarRepository usingCarRepository = new CarRepositoryImpl();

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
        carService.addCar(car);
        Car viewed = carService.viewCar(car.getCarId());

        assertEquals(car.getCarId(), viewed.getCarId());
    }

    @Test
    void shouldUpdateSuccessfully() {
        CarModelRepository usingCarModelRepository = new CarModelRepositoryImpl();
        CarDetailsRepository usingCarDetailsRepository = new CarDetailsRepositoryImpl();
        CarRepository usingCarRepository = new CarRepositoryImpl();

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
        carService.addCar(car);

        Car updated = Car.builder()
                .carId(car.getCarId())
                .carName(car.getCarName())
                .configuration(car.getConfiguration())
                .color(Color.cyan)
                .price(BigDecimal.valueOf(20000))
                .availableForTestDrive(true)
                .availableForSale(true)
                .build();

        carService.updateCar(updated);

        assertEquals(updated.getCarId(), carService.viewCar(car.getCarId()).getCarId());
        assertEquals(BigDecimal.valueOf(20000), carService.viewCar(car.getCarId()).getPrice());
    }

}
