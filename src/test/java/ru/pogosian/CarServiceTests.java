package ru.pogosian;

import org.junit.jupiter.api.Test;
import ru.pogosian.business.cars.*;
import ru.pogosian.business.filters.Filter;
import ru.pogosian.business.repositories.CarConfigurationRepository;
import ru.pogosian.business.repositories.CarDetailsRepository;
import ru.pogosian.business.repositories.CarModelRepository;
import ru.pogosian.business.repositories.CarRepository;
import ru.pogosian.business.services.CarService;
import ru.pogosian.business.services.CarServiceImpl;

import java.awt.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CarServiceTests {
    @Test
    void shouldAddCarAndViewCar(){
        CarModelRepository usingCarModelRepository = mock(CarModelRepository.class);
        CarDetailsRepository usingCarDetailsRepository = mock(CarDetailsRepository.class);
        CarRepository usingCarRepository = mock(CarRepository.class);
        CarConfigurationRepository usingCarConfigurationRepository = mock(CarConfigurationRepository.class);

        CarService carService = new CarServiceImpl(usingCarDetailsRepository, usingCarRepository, usingCarModelRepository, usingCarConfigurationRepository);

        UUID modelId = UUID.randomUUID();
        CarModel model = CarModel.builder()
                .modelId(modelId)
                .modelBrand("jiga")
                .modelName("4445")
                .basePrice(BigDecimal.valueOf(10000))
                .build();

        when(usingCarModelRepository.findById(modelId)).thenReturn(model);

        Car car = carService.CreateCarFromModel(modelId, "vishnewauya 7", ColorTypes.WHITE, true, true);
        carService.addCar(car);
        when(usingCarRepository.findById(car.getCarId())).thenReturn(car);
        Car viewed = carService.viewCar(car.getCarId());

        assertEquals(car.getCarId(), viewed.getCarId());
    }

    @Test
    void shouldUpdateSuccessfully() {
        CarModelRepository usingCarModelRepository = mock(CarModelRepository.class);
        CarDetailsRepository usingCarDetailsRepository = mock(CarDetailsRepository.class);
        CarRepository usingCarRepository = mock(CarRepository.class);
        CarConfigurationRepository usingCarConfigurationRepository = mock(CarConfigurationRepository.class);

        CarService carService = new CarServiceImpl(usingCarDetailsRepository, usingCarRepository, usingCarModelRepository, usingCarConfigurationRepository);

        UUID modelId = UUID.randomUUID();
        CarModel model = CarModel.builder()
                .modelId(modelId)
                .modelBrand("jiga")
                .modelName("4445")
                .basePrice(BigDecimal.valueOf(10000))
                .build();

        when(usingCarModelRepository.findById(modelId)).thenReturn(model);

        Car car = carService.CreateCarFromModel(modelId, "vishnewauya 7", ColorTypes.WHITE, true, true);
        carService.addCar(car);

        Car updated = Car.builder()
                .carId(car.getCarId())
                .carName(car.getCarName())
                .configuration(car.getConfiguration())
                .color(ColorTypes.WHITE)
                .price(BigDecimal.valueOf(20000))
                .availableForTestDrive(true)
                .availableForSale(true)
                .build();

        carService.updateCar(updated);
        when(usingCarRepository.findById(car.getCarId())).thenReturn(updated);
        assertEquals(updated.getCarId(), carService.viewCar(car.getCarId()).getCarId());
        assertEquals(BigDecimal.valueOf(20000), carService.viewCar(car.getCarId()).getPrice());
    }
    @Test
    void shouldFilterCars(){
        CarModelRepository usingCarModelRepository = mock(CarModelRepository.class);
        CarDetailsRepository usingCarDetailsRepository = mock(CarDetailsRepository.class);
        CarRepository usingCarRepository = mock(CarRepository.class);
        CarConfigurationRepository usingCarConfigurationRepository = mock(CarConfigurationRepository.class);


        CarService carService = new CarServiceImpl(usingCarDetailsRepository, usingCarRepository, usingCarModelRepository,  usingCarConfigurationRepository);

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

        when(usingCarModelRepository.findById(modelId1)).thenReturn(model1);
        when(usingCarModelRepository.findById(modelId2)).thenReturn(model2);


        Car car1 = carService.CreateCarFromModel(modelId1, "1", ColorTypes.WHITE, true, true);
        Car car2 = carService.CreateCarFromModel(modelId2, "2", ColorTypes.WHITE, true, true);

        carService.addCar(car1);
        carService.addCar(car2);

        Filter.CarFilter filter = new Filter.CarFilter(
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(10000000),
                new HashSet<>(Set.of(ColorTypes.WHITE)),
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
        when(usingCarRepository.findAllByFilter(filter)).thenReturn(List.of(car2));
        List<Car> result = carService.filteredCars(filter);
        assertEquals(1, result.size());
        assertEquals(car2,  result.getFirst());
    }
}