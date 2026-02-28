package ru.pogosian.infrastructure.repository;

import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.repositories.InStockCarOrderRepository;

import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InStockCarOrderRepositoryImpl implements InStockCarOrderRepository {
    private Map<UUID, InStockCarOrder> store =  new HashMap<UUID, InStockCarOrder>();
    @Override
    public void save(InStockCarOrder InStockCarOrder) {
        store.put(InStockCarOrder.getOrderId(), InStockCarOrder);
    }

    @Override
    public InStockCarOrder findById(UUID id) {
        InStockCarOrder InStockCarOrder = store.get(id);
        return InStockCarOrder;
    }

    @Override
    public List<InStockCarOrder> findAll() {
        return new ArrayList<InStockCarOrder>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }
}
