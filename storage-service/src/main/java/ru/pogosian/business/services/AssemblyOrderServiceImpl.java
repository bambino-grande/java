package ru.pogosian.business.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.pogosian.business.assembly.AssemblyOrder;
import ru.pogosian.business.assembly.AssemblyOrderStatus;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.outbox.OutboxEvent;
import ru.pogosian.business.outbox.OutboxStatus;
import ru.pogosian.business.repositories.AssemblyOrderRepository;
import ru.pogosian.business.repositories.CarRepository;
import ru.pogosian.business.repositories.OutboxEventRepository;
import ru.pogosian.config.RabbitMQConfig;
import ru.pogosian.messaging.events.OrderApproved;
import ru.pogosian.messaging.events.OrderRejected;
import ru.pogosian.messaging.events.OrderSentForApproval;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssemblyOrderServiceImpl implements AssemblyOrderService {
    private final AssemblyOrderRepository assemblyOrderRepository;
    private final CarRepository carRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;
    @Transactional
    @Override
    public AssemblyOrder addAssemblyOrder(AssemblyOrder assemblyOrder) {
        assemblyOrderRepository.save(assemblyOrder);
        return assemblyOrder;
    }

    @Transactional
    @Override
    public AssemblyOrder updateAssemblyOrder(UUID id, AssemblyOrder assemblyOrder) {
        AssemblyOrder order = viewAssemblyOrder(id);
        AssemblyOrder change = new AssemblyOrder(order.getId(), assemblyOrder.getSourceOrderId(),assemblyOrder.getStatus());
        assemblyOrderRepository.save(change);
        return change;
    }

    @Transactional
    @Override
    public void deleteAssemblyOrder(UUID AssemblyOrderId) {
        assemblyOrderRepository.deleteById(AssemblyOrderId);
    }

    @Override
    public AssemblyOrder viewAssemblyOrder(UUID AssemblyOrderId) {
        return assemblyOrderRepository.findById(AssemblyOrderId);
    }

    @Override
    public List<AssemblyOrder> viewAllAssemblyOrder(Pageable pageable) {
        return assemblyOrderRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void processOrderSentForApproval(OrderSentForApproval orderSentForApproval) {
        try {
            Car car = carRepository.findById(orderSentForApproval.carId());
            UUID messageId = UUID.randomUUID();
            if (car.getAvailableForSale() == true) {
                OrderApproved orderApproved = new OrderApproved(
                        messageId,
                        orderSentForApproval.orderId(),
                        orderSentForApproval.orderType(),
                        orderSentForApproval.traceId()
                );
                assemblyOrderRepository.save(new AssemblyOrder(
                        UUID.randomUUID(),
                        orderSentForApproval.orderId(),
                        AssemblyOrderStatus.ASSEMBLED)
                );
                outboxEventRepository.save(new OutboxEvent(
                                messageId,
                                orderSentForApproval.orderId(),
                                RabbitMQConfig.ROUTING_KEY_APPROVED,
                                objectMapper.writeValueAsString(orderApproved),
                                orderSentForApproval.traceId(),
                                OutboxStatus.PENDING
                        )
                );
            }
            else{
                OrderRejected orderRejected = new OrderRejected(
                        messageId,
                        orderSentForApproval.orderId(),
                        orderSentForApproval.orderType(),
                        orderSentForApproval.traceId(),
                        "car is not available for sale"
                );
                assemblyOrderRepository.save(new AssemblyOrder(
                        UUID.randomUUID(),
                        orderSentForApproval.orderId(),
                        AssemblyOrderStatus.FAIL)
                );
                outboxEventRepository.save(new OutboxEvent(
                                messageId,
                                orderSentForApproval.orderId(),
                                RabbitMQConfig.ROUTING_KEY_REJECTED,
                                objectMapper.writeValueAsString(orderRejected),
                                orderSentForApproval.traceId(),
                                OutboxStatus.PENDING
                        )
                );
            }
        } catch (Exception e){
            throw new DomainValidationException(e.getMessage());
        }
    }
}
