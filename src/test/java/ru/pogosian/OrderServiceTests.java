package ru.pogosian;

import org.junit.jupiter.api.Test;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.cars.CarModel;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.repositories.*;
import ru.pogosian.business.services.CarService;
import ru.pogosian.business.services.CarServiceImpl;
import ru.pogosian.business.services.OrderService;
import ru.pogosian.business.services.OrderServiceImpl;
import ru.pogosian.infrastructure.repository.*;

import java.awt.*;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTests {
    @Test
    public void shouldCreateAndListOrders() {
        CarModelRepository usingCarModelRepository = new CarModelRepositoryImpl();
        CarDetailsRepository usingCarDetailsRepository = new CarDetailsRepositoryImpl();
        CarRepository usingCarRepository = new CarRepositoryImpl();
        InStockCarOrderRepository usingInStockCarOrderRepository = new InStockCarOrderRepositoryImpl();
        ComplectationCarOrderRepository usingComplectationCarOrderRepository = new ComplectationCarOrderRepositoryImpl();

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

        OrderService service = new OrderServiceImpl(usingInStockCarOrderRepository, usingComplectationCarOrderRepository, usingCarRepository);

        InStockCarOrder inStockCarOrder = service.createInStockCarOrder(car.getCarId(), UUID.randomUUID(), UUID.randomUUID());
        ComplectationCarOrder complectationCarOrder = service.createComplectationCarOrder(car.getCarId(), UUID.randomUUID(), UUID.randomUUID());

        assertEquals(car.getCarId(), inStockCarOrder.getCarId());
        assertEquals(car.getCarId(), complectationCarOrder.getCarId());
    }
}
