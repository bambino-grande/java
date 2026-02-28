package ru.pogosian.business.services;

import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final InStockCarOrderRepository inStockCarOrderRepository;
    private final ComplectationCarOrderRepository complectationCarOrderRepository;
    private final CarRepository  carRepository;


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

    @Override
    public InStockCarOrder updateInStockCarOrder(InStockCarOrder inStockCarOrder) {
        if(inStockCarOrderRepository.findById(inStockCarOrder.getOrderId()) == null)
            throw new DomainValidationException("In Stock Car Order Not Found");
        inStockCarOrderRepository.save(inStockCarOrder);
        return inStockCarOrder;
    }

    @Override
    public ComplectationCarOrder updateComplectationCarOrder(ComplectationCarOrder complectationCarOrder) {
        if(complectationCarOrderRepository.findById(complectationCarOrder.getOrderId()) == null)
            throw new DomainValidationException("Complectation Car Order Not Found");
        complectationCarOrderRepository.save(complectationCarOrder);
        return complectationCarOrder;
    }

    @Override
    public List<InStockCarOrder> viewAllInStockCarOrders() {
        return  inStockCarOrderRepository.findAll();
    }

    @Override
    public List<ComplectationCarOrder> viewAllComplectationCarOrders() {
        return  complectationCarOrderRepository.findAll();
    }

    @Override
    public void deleteInStockCarOrder(UUID orderID) {
        inStockCarOrderRepository.deleteById(orderID);
    }

    @Override
    public void deleteComplectationCarOrder(UUID orderID) {
        complectationCarOrderRepository.deleteById(orderID);
    }
}
