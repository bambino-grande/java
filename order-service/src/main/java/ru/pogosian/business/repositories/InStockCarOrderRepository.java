package ru.pogosian.business.repositories;

import org.springframework.data.domain.Pageable;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;

import java.util.UUID;
import java.util.List;

public interface InStockCarOrderRepository {
    void save(InStockCarOrder order);
    InStockCarOrder findById(UUID id);
    List<InStockCarOrder> findAll(Pageable pageable);
    void deleteById(UUID id);
    List<InStockCarOrder> findAllByClientId(UUID clientId, Pageable pageable);
}
