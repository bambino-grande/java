package ru.pogosian.business.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.orders.complectationCarOrder.CompectationCarOrderStatusState;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrderPlaced;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.repositories.CarRepository;
import ru.pogosian.business.repositories.ComplectationCarOrderRepository;
import ru.pogosian.business.repositories.InStockCarOrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final InStockCarOrderRepository inStockCarOrderRepository;
    private final ComplectationCarOrderRepository complectationCarOrderRepository;
    private final CarRepository  carRepository;

    @Transactional
    @Override
    public InStockCarOrder createInStockCarOrder(UUID carID, UUID clientID, UUID managerID) {
        InStockCarOrder order = InStockCarOrder.builder()
                .orderId(UUID.randomUUID())
                .clientId(clientID)
                .managerId(managerID)
                .carId(carID)
                .build();
        inStockCarOrderRepository.save(order);
        return order;
    }

    @Transactional
    @Override
    public ComplectationCarOrder createComplectationCarOrder(UUID carID, UUID clientID, UUID managerID) {
        ComplectationCarOrder order = ComplectationCarOrder.builder()
                .orderId(UUID.randomUUID())
                .clientId(clientID)
                .managerId(managerID)
                .carId(carID)
                .build();
        complectationCarOrderRepository.save(order);
        return order;
    }

    @Transactional
    @Override
    public InStockCarOrder updateInStockCarOrder(InStockCarOrder inStockCarOrder) {
        inStockCarOrderRepository.save(inStockCarOrder);
        return inStockCarOrder;
    }

    @Transactional
    @Override
    public ComplectationCarOrder updateComplectationCarOrder(ComplectationCarOrder complectationCarOrder) {
        complectationCarOrderRepository.save(complectationCarOrder);
        return complectationCarOrder;
    }

    @Override
    public List<InStockCarOrder> viewAllInStockCarOrders(Pageable pageable) {
        return  inStockCarOrderRepository.findAll(pageable);
    }

    @Override
    public List<ComplectationCarOrder> viewAllComplectationCarOrders(Pageable pageable) {
        return  complectationCarOrderRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteInStockCarOrder(UUID orderID) {
        inStockCarOrderRepository.deleteById(orderID);
    }

    @Transactional
    @Override
    public void deleteComplectationCarOrder(UUID orderID) {
        complectationCarOrderRepository.deleteById(orderID);
    }
}
