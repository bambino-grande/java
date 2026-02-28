package ru.pogosian.infrastructure.repository;

import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.repositories.ComplectationCarOrderRepository;

import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplectationCarOrderRepositoryImpl implements ComplectationCarOrderRepository {
    private Map<UUID, ComplectationCarOrder> store =  new HashMap<UUID, ComplectationCarOrder>();
    @Override
    public void save(ComplectationCarOrder ComplectationCarOrder) {
        store.put(ComplectationCarOrder.getOrderId(), ComplectationCarOrder);
    }

    @Override
    public ComplectationCarOrder findById(UUID id) {
        ComplectationCarOrder ComplectationCarOrder = store.get(id);
        return ComplectationCarOrder;
    }

    @Override
    public List<ComplectationCarOrder> findAll() {
        return new ArrayList<ComplectationCarOrder>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }
}
