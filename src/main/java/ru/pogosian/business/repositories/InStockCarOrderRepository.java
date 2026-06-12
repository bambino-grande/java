package ru.pogosian.business.repositories;

import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;

import java.util.UUID;
import java.util.List;

public interface InStockCarOrderRepository {
    void save(InStockCarOrder order);
    InStockCarOrder findById(UUID id);
    List<InStockCarOrder> findAll();
    void deleteById(UUID id);
}
