package ru.pogosian;

import org.junit.jupiter.api.Test;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.repositories.*;
import ru.pogosian.business.services.OrderService;
import ru.pogosian.business.services.OrderServiceImpl;
import ru.pogosian.business.users.Manager;
import ru.pogosian.security.OrderSecurityService;
import ru.pogosian.security.SecurityService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTests {
    @Test
    public void shouldCreateAndListOrders() {
        InStockCarOrderRepository usingInStockCarOrderRepository = mock(InStockCarOrderRepository.class);
        ComplectationCarOrderRepository usingComplectationCarOrderRepository = mock(ComplectationCarOrderRepository.class);
        UserRepository usingUserRepository = mock(UserRepository.class);
        SecurityService securityService = mock(SecurityService.class);
        OrderSecurityService orderSecurityService = mock(OrderSecurityService.class);

        UUID managerId = UUID.randomUUID();
        Manager manager = mock(Manager.class);
        when(manager.getId()).thenReturn(managerId);
        when(usingUserRepository.findAllManagers()).thenReturn(List.of(manager));

        OrderService service = new OrderServiceImpl(
                usingInStockCarOrderRepository,
                usingComplectationCarOrderRepository,
                usingUserRepository,
                securityService,
                orderSecurityService);

        UUID carId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();

        InStockCarOrder inStockCarOrder = service.createInStockCarOrder(carId, clientId);
        ComplectationCarOrder complectationCarOrder = service.createComplectationCarOrder(carId, clientId);

        assertEquals(carId, inStockCarOrder.getCarId());
        assertEquals(carId, complectationCarOrder.getCarId());

        assertEquals(clientId, inStockCarOrder.getClientId());
        assertEquals(clientId, complectationCarOrder.getClientId());
    }
}
