package ru.pogosian;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pogosian.business.repositories.InStockCarOrderRepository;
import ru.pogosian.business.repositories.UserRepository;
import ru.pogosian.messaging.events.OrderRejected;

import java.util.UUID;

public class RepositoryIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InStockCarOrderRepository inStockCarOrderRepository;

    @Test
    void userRepositoryFindsSeedUser(){
        var user = userRepository.findById(UUID.fromString("10000000-0000-0000-0000-000000000001"));
        Assertions.assertEquals("petya", user.getName());
    }

    @Test
    void orderRepositoryFindsSeedOrder(){
        var order = inStockCarOrderRepository.findById(UUID.fromString("70000000-0000-0000-0000-000000000001"));
        Assertions.assertEquals(UUID.fromString("40000000-0000-0000-0000-000000000001"), order.getCarId());
    }
}
