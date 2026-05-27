package ru.pogosian.business.services;

import org.springframework.data.domain.Pageable;
import ru.pogosian.business.assembly.AssemblyOrder;
import ru.pogosian.messaging.events.OrderSentForApproval;

import java.util.List;
import java.util.UUID;

public interface AssemblyOrderService {
    AssemblyOrder addAssemblyOrder(AssemblyOrder assemblyOrder);
    AssemblyOrder updateAssemblyOrder(UUID id, AssemblyOrder assemblyOrder);
    void deleteAssemblyOrder(UUID AssemblyOrderId);
    AssemblyOrder viewAssemblyOrder(UUID AssemblyOrderId);
    List<AssemblyOrder> viewAllAssemblyOrder(Pageable pageable);
    void processOrderSentForApproval(OrderSentForApproval orderSentForApproval);
}
