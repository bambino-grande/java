package ru.pogosian.business.repositories;

import org.springframework.data.domain.Pageable;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;

import java.util.UUID;
import java.util.List;

public interface ComplectationCarOrderRepository {
    void save(ComplectationCarOrder order);
    ComplectationCarOrder findById(UUID id);
    List<ComplectationCarOrder> findAll(Pageable pageable);
    void deleteById(UUID id);
    List<ComplectationCarOrder> findAllByClientId(UUID clientId, Pageable pageable);
}
