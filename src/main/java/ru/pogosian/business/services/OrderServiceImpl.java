package ru.pogosian.business.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.orders.complectationCarOrder.CompectationCarOrderStatusState;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrderCancelled;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrderPlaced;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrderCancelled;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrderStatusState;
import ru.pogosian.business.repositories.CarRepository;
import ru.pogosian.business.repositories.ComplectationCarOrderRepository;
import ru.pogosian.business.repositories.InStockCarOrderRepository;
import ru.pogosian.business.repositories.UserRepository;
import ru.pogosian.business.users.Client;
import ru.pogosian.business.users.Manager;
import ru.pogosian.business.users.SystemAdmin;
import ru.pogosian.business.users.User;
import ru.pogosian.security.OrderSecurityService;
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
    private final OrderSecurityService orderSecurityService;

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
        User currnetUser = securityService.getCurrentUser();
        if(orderSecurityService.isInStockCarOrderOwner(orderID, SecurityContextHolder.getContext().getAuthentication()) || currnetUser instanceof Manager || currnetUser instanceof SystemAdmin)
            return inStockCarOrderRepository.findById(orderID);
        throw new IllegalArgumentException("You are not allowed to perform this operation");
    }

    @Override
    public ComplectationCarOrder getComplectationCarOrder(UUID orderID) {
        User currnetUser = securityService.getCurrentUser();
        if(currnetUser instanceof Manager || currnetUser instanceof SystemAdmin || orderSecurityService.isComplectationCarOrderOwner(orderID, SecurityContextHolder.getContext().getAuthentication()))
            return complectationCarOrderRepository.findById(orderID);
        throw new IllegalArgumentException("You are not allowed to perform this operation");
    }

    @Transactional
    @Override
    public InStockCarOrder cancelInStockCarOrder(UUID orderID) {
        User currnetUser = securityService.getCurrentUser();
        if(currnetUser instanceof SystemAdmin || orderSecurityService.isInStockCarOrderOwner(orderID, SecurityContextHolder.getContext().getAuthentication())) {
            InStockCarOrder inStockCarOrder = inStockCarOrderRepository.findById(orderID);
            inStockCarOrder.setState(new InStockCarOrderCancelled());
            inStockCarOrderRepository.save(inStockCarOrder);
            return inStockCarOrder;
        }
        throw new IllegalArgumentException("You are not allowed to cancel this order");
    }

    @Transactional
    @Override
    public ComplectationCarOrder cancelComplectationCarOrder(UUID orderID) {
        User currnetUser = securityService.getCurrentUser();
        if(currnetUser instanceof SystemAdmin || orderSecurityService.isComplectationCarOrderOwner(orderID, SecurityContextHolder.getContext().getAuthentication())) {
            ComplectationCarOrder complectationCarOrder = complectationCarOrderRepository.findById(orderID);
            complectationCarOrder.setState(new ComplectationCarOrderCancelled());
            complectationCarOrderRepository.save(complectationCarOrder);
            return complectationCarOrder;
        }
        throw new IllegalArgumentException("You are not allowed to cancel this order");
    }

    @Transactional
    @Override
    public InStockCarOrder moveInStockCarOrder(UUID orderID) {
        if(orderSecurityService.canMoveInStockCarOrder(orderID, SecurityContextHolder.getContext().getAuthentication())) {
            InStockCarOrder inStockCarOrder = inStockCarOrderRepository.findById(orderID);
            inStockCarOrder.nextState();
            inStockCarOrderRepository.save(inStockCarOrder);
            return inStockCarOrder;
        }
        throw new IllegalArgumentException("You are not allowed to move in stock car order");
    }

    @Transactional
    @Override
    public ComplectationCarOrder moveComplectationCarOrder(UUID orderID) {
        if(orderSecurityService.canMoveComplectationCarOrder(orderID, SecurityContextHolder.getContext().getAuthentication())) {
            ComplectationCarOrder complectationCarOrder = complectationCarOrderRepository.findById(orderID);
            complectationCarOrder.nextState();
            complectationCarOrderRepository.save(complectationCarOrder);
            return complectationCarOrder;
        }
        throw new IllegalArgumentException("You are not allowed to move this that order");
    }

    private UUID findManagerId() {
        return userRepository.findAllManagers().stream().findAny().map(User::getId).orElseThrow(()->new DomainValidationException("no manager have been found"));
    }
}
