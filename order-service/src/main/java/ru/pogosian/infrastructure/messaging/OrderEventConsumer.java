package ru.pogosian.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrderCancelled;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrderIsReadyForPickingUp;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrderCancelled;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrderIsReadyForPickingUp;
import ru.pogosian.business.repositories.ComplectationCarOrderRepository;
import ru.pogosian.business.repositories.InStockCarOrderRepository;
import ru.pogosian.business.repositories.ProcessedEventRepository;
import ru.pogosian.config.RabbitMQConfig;
import ru.pogosian.messaging.OrderType;
import ru.pogosian.messaging.events.OrderApproved;
import ru.pogosian.messaging.events.OrderRejected;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {
    private final ProcessedEventRepository processedEventRepository;
    private final InStockCarOrderRepository inStockCarOrderRepository;
    private final ComplectationCarOrderRepository complectationCarOrderRepository;

    @RabbitListener(queues = RabbitMQConfig.ORDER_APPROVED_QUEUE)
    @Transactional
    public void handleOrderApproved(byte[] message) throws IOException {
        OrderApproved orderApproved = new ObjectMapper().readValue(message, OrderApproved.class);
        if(processedEventRepository.existsByEventId(orderApproved.messageId()))
            return;
        processedEventRepository.save(orderApproved.messageId());

        if(orderApproved.orderType() == OrderType.IN_STOCK){
            InStockCarOrder inStockCarOrder = inStockCarOrderRepository.findById(orderApproved.orderId());
            inStockCarOrder.setState(new InStockCarOrderIsReadyForPickingUp());
            inStockCarOrderRepository.save(inStockCarOrder);
        }
        else{
            ComplectationCarOrder complectationCarOrder = complectationCarOrderRepository.findById(orderApproved.orderId());
            complectationCarOrder.setState(new ComplectationCarOrderIsReadyForPickingUp());
            complectationCarOrderRepository.save(complectationCarOrder);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_REJECTED_QUEUE)
    @Transactional
    public void handleOrderRejected(byte[] message) throws IOException {
        OrderRejected orderRejected = new ObjectMapper().readValue(message, OrderRejected.class);
        if(processedEventRepository.existsByEventId(orderRejected.messageId()))
            return;
        processedEventRepository.save(orderRejected.messageId());

        if(orderRejected.orderType() == OrderType.IN_STOCK){
            InStockCarOrder inStockCarOrder = inStockCarOrderRepository.findById(orderRejected.orderId());
            inStockCarOrder.setState(new InStockCarOrderCancelled());
            inStockCarOrderRepository.save(inStockCarOrder);
        }
        else{
            ComplectationCarOrder complectationCarOrder = complectationCarOrderRepository.findById(orderRejected.orderId());
            complectationCarOrder.setState(new ComplectationCarOrderCancelled());
            complectationCarOrderRepository.save(complectationCarOrder);
        }
    }
}
