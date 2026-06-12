package ru.pogosian.business.repositories;

import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;

import java.util.UUID;
import java.util.List;

public interface ComplectationCarOrderRepository {
    void save(ComplectationCarOrder order);
    ComplectationCarOrder findById(UUID id);
    List<ComplectationCarOrder> findAll();
    void deleteById(UUID id);
}
