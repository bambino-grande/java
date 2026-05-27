package ru.pogosian.business.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrderCancelled;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrderPayed;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrderCancelled;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrderPayed;
import ru.pogosian.business.outbox.OutboxEvent;
import ru.pogosian.business.outbox.OutboxStatus;
import ru.pogosian.business.repositories.ComplectationCarOrderRepository;
import ru.pogosian.business.repositories.InStockCarOrderRepository;
import ru.pogosian.business.repositories.OutboxEventRepository;
import ru.pogosian.business.repositories.UserRepository;
import ru.pogosian.business.users.Manager;
import ru.pogosian.business.users.SystemAdmin;
import ru.pogosian.business.users.User;
import ru.pogosian.config.RabbitMQConfig;
import ru.pogosian.messaging.OrderType;
import ru.pogosian.messaging.events.OrderSentForApproval;
import ru.pogosian.security.SecurityService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final InStockCarOrderRepository inStockCarOrderRepository;
    private final ComplectationCarOrderRepository complectationCarOrderRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public InStockCarOrder createInStockCarOrder(UUID carID, UUID clientID) {
        InStockCarOrder order = InStockCarOrder.builder()
                .orderId(UUID.randomUUID())
                .clientId(clientID)
                .managerId(findManagerId())
                .carId(carID)
                .build();
        inStockCarOrderRepository.save(order);
        return order;
    }

    @Transactional
    @Override
    public InStockCarOrder createUsersInStockCarOrder(UUID carID) {
        User currnetUser = securityService.getCurrentUser();
        InStockCarOrder order = InStockCarOrder.builder()
                .orderId(UUID.randomUUID())
                .clientId(currnetUser.getId())
                .managerId(findManagerId())
                .carId(carID)
                .build();
        inStockCarOrderRepository.save(order);
        return order;
    }

    @Transactional
    @Override
    public ComplectationCarOrder createComplectationCarOrder(UUID carID, UUID clientID) {
        ComplectationCarOrder order = ComplectationCarOrder.builder()
                .orderId(UUID.randomUUID())
                .clientId(clientID)
                .managerId(findManagerId())
                .carId(carID)
                .build();
        complectationCarOrderRepository.save(order);
        return order;
    }
    @Transactional
    @Override
    public ComplectationCarOrder createUsrsComplectationCarOrder(UUID carID) {
        User currnetUser = securityService.getCurrentUser();
        ComplectationCarOrder order = ComplectationCarOrder.builder()
                .orderId(UUID.randomUUID())
                .clientId(currnetUser.getId())
                .managerId(findManagerId())
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
        User currnetUser = securityService.getCurrentUser();
        if(currnetUser instanceof Manager || currnetUser instanceof SystemAdmin)
            return  inStockCarOrderRepository.findAll(pageable);
        else
            return inStockCarOrderRepository.findAllByClientId(currnetUser.getId(), pageable);
    }

    @Override
    public List<ComplectationCarOrder> viewAllComplectationCarOrders(Pageable pageable) {
        User currnetUser = securityService.getCurrentUser();
        if(currnetUser instanceof Manager || currnetUser instanceof SystemAdmin)
            return  complectationCarOrderRepository.findAll(pageable);
        else
            return complectationCarOrderRepository.findAllByClientId(currnetUser.getId(), pageable);
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

    @Override
    public InStockCarOrder getInStockCarOrder(UUID orderID) {
        return inStockCarOrderRepository.findById(orderID);
    }

    @Override
    public ComplectationCarOrder getComplectationCarOrder(UUID orderID) {
        return complectationCarOrderRepository.findById(orderID);
    }

    @Transactional
    @Override
    public InStockCarOrder cancelInStockCarOrder(UUID orderID) {
        InStockCarOrder inStockCarOrder = inStockCarOrderRepository.findById(orderID);
        inStockCarOrder.setState(new InStockCarOrderCancelled());
        inStockCarOrderRepository.save(inStockCarOrder);
        return inStockCarOrder;
    }

    @Transactional
    @Override
    public ComplectationCarOrder cancelComplectationCarOrder(UUID orderID) {
        ComplectationCarOrder complectationCarOrder = complectationCarOrderRepository.findById(orderID);
        complectationCarOrder.setState(new ComplectationCarOrderCancelled());
        complectationCarOrderRepository.save(complectationCarOrder);
        return complectationCarOrder;
    }

    @Transactional
    @Override
    public InStockCarOrder moveInStockCarOrder(UUID orderID) {
        InStockCarOrder inStockCarOrder = inStockCarOrderRepository.findById(orderID);
        inStockCarOrder.nextState();
        inStockCarOrderRepository.save(inStockCarOrder);

        if(inStockCarOrder.getState() instanceof InStockCarOrderPayed) {
            sendOrderForWarehouseApproval(inStockCarOrder.getOrderId(),inStockCarOrder.getCarId(), OrderType.IN_STOCK);
        }
        return inStockCarOrder;
    }

    @Transactional
    @Override
    public ComplectationCarOrder moveComplectationCarOrder(UUID orderID) {
        ComplectationCarOrder complectationCarOrder = complectationCarOrderRepository.findById(orderID);
        complectationCarOrder.nextState();
        complectationCarOrderRepository.save(complectationCarOrder);

        if(complectationCarOrder.getState() instanceof ComplectationCarOrderPayed) {
            sendOrderForWarehouseApproval(complectationCarOrder.getOrderId(),complectationCarOrder.getCarId(), OrderType.COMPLECTATION);
        }
        return complectationCarOrder;
    }

    private UUID findManagerId() {
        return userRepository.findAllManagers().stream().findAny().map(User::getId).orElseThrow(()->new DomainValidationException("no manager have been found"));
    }

    private void sendOrderForWarehouseApproval(UUID orderId, UUID carId, OrderType orderType){
        UUID traceId = UUID.randomUUID();
        OrderSentForApproval event = new OrderSentForApproval(
                UUID.randomUUID(),
                orderId,
                orderType,
                traceId,
                carId
        );
        try {
            String message = objectMapper.writeValueAsString(event);
            outboxEventRepository.save(new OutboxEvent(
                    event.messageId(),
                    orderId,
                    RabbitMQConfig.ROUTING_KEY_SENT_FOR_APPROVAL,
                    message,
                    traceId,
                    OutboxStatus.PENDING
            ));
        }
        catch (Exception ignored) {
            throw new DomainValidationException("can't sent order for approval");
        }
    }
}
