package ru.pogosian;

import org.junit.jupiter.api.Test;
import ru.pogosian.business.cars.*;
import ru.pogosian.business.filters.Filter;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    @Test
    void shouldFilterCars(){
        CarModelRepository usingCarModelRepository = new CarModelRepositoryImpl();
        CarDetailsRepository usingCarDetailsRepository = new CarDetailsRepositoryImpl();
        CarRepository usingCarRepository = new CarRepositoryImpl();

        CarService carService = new CarServiceImpl(usingCarDetailsRepository, usingCarRepository, usingCarModelRepository);

        UUID modelId1 = UUID.randomUUID();
        CarModel model1 = CarModel.builder()
                .modelId(modelId1)
                .modelBrand("jiga")
                .modelName("4445")
                .basePrice(BigDecimal.valueOf(10000))
                .build();

        UUID modelId2 = UUID.randomUUID();
        CarModel model2 = CarModel.builder()
                .modelId(modelId2)
                .modelBrand("NEjiga")
                .modelName("44455")
                .basePrice(BigDecimal.valueOf(600))
                .build();

        usingCarModelRepository.save(model1);
        usingCarModelRepository.save(model2);

        Car car1 = carService.CreateCarFromModel(modelId1, "1", Color.black, true, true);
        Car car2 = carService.CreateCarFromModel(modelId2, "2", Color.GREEN, true, true);

        carService.addCar(car1);
        carService.addCar(car2);

        Filter.CarFilter filter = new Filter.CarFilter(
        BigDecimal.valueOf(500),
        BigDecimal.valueOf(10000000),
        new HashSet<Color>(Set.of(Color.GREEN)),
        new HashSet<String>(Set.of("NEjiga")),
        new HashSet<String>(Set.of("44455")),
        0,
        100000,
        0.0,
        100.0,
        null,
        null,
        null,
        null
        );
        List<Car> result = carService.filteredCars(filter);
        assertEquals(1, result.size());
        assertEquals(car2,  result.getFirst());
    }
}