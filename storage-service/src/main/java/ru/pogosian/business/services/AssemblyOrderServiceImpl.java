package ru.pogosian.business.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.pogosian.business.assembly.AssemblyOrder;
import ru.pogosian.business.assembly.AssemblyOrderStatus;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.outbox.OutboxEvent;
import ru.pogosian.business.outbox.OutboxStatus;
import ru.pogosian.business.repositories.*;
import ru.pogosian.config.RabbitMQConfig;
import ru.pogosian.messaging.events.OrderApproved;
import ru.pogosian.messaging.events.OrderRejected;
import ru.pogosian.messaging.events.OrderSentForApproval;
import ru.pogosian.security.SecurityService;

import java.lang.module.Configuration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssemblyOrderServiceImpl implements AssemblyOrderService {
    private final AssemblyOrderRepository assemblyOrderRepository;
    private final CarRepository carRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;
    private final CarModelRepository carModelRepository;
    private final SecurityService securityService;
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
        AssemblyOrder change = new AssemblyOrder(
                order.getId(),
                assemblyOrder.getSourceOrderId(),
                assemblyOrder.getStatus(),
                assemblyOrder.isRemoved(),
                assemblyOrder.getOrderType(),
                assemblyOrder.getCarId(),
                assemblyOrder.getRequiredDetailIds(),
                assemblyOrder.getWarehouseEmployeeId()
        );
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
                        AssemblyOrderStatus.ASSEMBLED,
                        false,
                        orderSentForApproval.orderType(),
                        orderSentForApproval.carId(),
                        carModelRepository.findById(carRepository.findById(orderSentForApproval.carId()).getConfiguration().getConfigurationModelId()).getDetails().stream().map(CarDetails::getId).collect(Collectors.toSet()),
                        securityService.getCurrentUser().getId()
                        )
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
                        AssemblyOrderStatus.FAIL,
                        false,
                        orderSentForApproval.orderType(),
                        orderSentForApproval.carId(),
                        carModelRepository.findById(carRepository.findById(orderSentForApproval.carId()).getConfiguration().getConfigurationModelId()).getDetails().stream().map(CarDetails::getId).collect(Collectors.toSet()),
                        securityService.getCurrentUser().getId()
                        )
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
