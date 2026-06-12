package ru.pogosian.business.services;

import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    InStockCarOrder createInStockCarOrder(UUID carID, UUID clientID, UUID managerID);
    ComplectationCarOrder createComplectationCarOrder(UUID carID, UUID clientID, UUID managerID);

    InStockCarOrder updateInStockCarOrder(InStockCarOrder inStockCarOrder);
    ComplectationCarOrder updateComplectationCarOrder(ComplectationCarOrder complectationCarOrder);

    List<InStockCarOrder> viewAllInStockCarOrders();
    List<ComplectationCarOrder> viewAllComplectationCarOrders();

    void deleteInStockCarOrder(UUID orderID);
    void deleteComplectationCarOrder(UUID orderID);
}
