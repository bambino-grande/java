package ru.pogosian.infrastructure.repository;

import ru.pogosian.business.excrptions.DomainValidationException;
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
        if(InStockCarOrder == null)
            throw new DomainValidationException("InStockCarOrder is null");
        store.put(InStockCarOrder.getOrderId(), InStockCarOrder);
    }

    @Override
    public InStockCarOrder findById(UUID id) {
        if(!store.containsKey(id))
            throw new DomainValidationException("Order id not found");
        InStockCarOrder InStockCarOrder = store.get(id);
        return InStockCarOrder;
    }

    @Override
    public List<InStockCarOrder> findAll() {
        return new ArrayList<InStockCarOrder>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        if(!store.containsKey(id))
            throw new DomainValidationException("Order id not found");
        store.remove(id);
    }
}
