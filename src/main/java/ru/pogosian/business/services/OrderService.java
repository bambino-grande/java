package ru.pogosian.business.services;

import org.springframework.data.domain.Pageable;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    InStockCarOrder createInStockCarOrder(UUID carID, UUID clientID);
    ComplectationCarOrder createComplectationCarOrder(UUID carID, UUID clientID);

    InStockCarOrder createUsersInStockCarOrder(UUID carID);
    ComplectationCarOrder createUsrsComplectationCarOrder(UUID carID);

    InStockCarOrder updateInStockCarOrder(InStockCarOrder inStockCarOrder);
    ComplectationCarOrder updateComplectationCarOrder(ComplectationCarOrder complectationCarOrder);

    List<InStockCarOrder> viewAllInStockCarOrders(Pageable pageable);
    List<ComplectationCarOrder> viewAllComplectationCarOrders(Pageable pageable);

    void deleteInStockCarOrder(UUID orderID);
    void deleteComplectationCarOrder(UUID orderID);

    InStockCarOrder getInStockCarOrder(UUID orderID);
    ComplectationCarOrder getComplectationCarOrder(UUID orderID);

    InStockCarOrder cancelInStockCarOrder(UUID orderID);
    ComplectationCarOrder cancelComplectationCarOrder(UUID orderID);

    InStockCarOrder moveInStockCarOrder(UUID orderID);
    ComplectationCarOrder moveComplectationCarOrder(UUID orderID);
}
