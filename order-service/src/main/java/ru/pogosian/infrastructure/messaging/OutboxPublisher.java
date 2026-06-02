package ru.pogosian.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrderCancelled;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrderCancelled;
import ru.pogosian.business.outbox.OutboxEvent;
import ru.pogosian.business.outbox.OutboxStatus;
import ru.pogosian.business.repositories.ComplectationCarOrderRepository;
import ru.pogosian.business.repositories.InStockCarOrderRepository;
import ru.pogosian.business.repositories.OutboxEventRepository;
import ru.pogosian.config.RabbitMQConfig;
import ru.pogosian.messaging.OrderType;
import ru.pogosian.messaging.events.OrderSentForApproval;

@Component
@Slf4j
@RequiredArgsConstructor
public class OutboxPublisher {
    private final OutboxEventRepository outboxEventRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ComplectationCarOrderRepository complectationCarOrderRepository;
    private final ObjectMapper objectMapper;
    private final InStockCarOrderRepository inStockCarOrderRepository;
    @Scheduled(fixedDelayString = "${outbox.publisher.fixed-delay-ms}")
    @Transactional
    public void publishPendingEvents() throws JsonProcessingException {
        for(OutboxEvent event : outboxEventRepository.findPendingForUpdate()){
            try(MDC.MDCCloseable ignored = MDC.putCloseable("traceId", event.getTraceId().toString())) {
                try {
                    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, event.getRoutingKey(), event.getMessage());
                    event.makeSend();
                    outboxEventRepository.save(event);
                    log.info("published outbox event id:{}", event.getId());
                } catch (Exception e) {
                    log.error("failed to publish outbox event id:{}", event.getId(), e);
                    event.increaseTryCount();
                    outboxEventRepository.save(event);
                    if(event.getTryCount() > 5) {
                        outboxEventRepository.delete(event);
                        OrderSentForApproval message = objectMapper.readValue(event.getMessage(), OrderSentForApproval.class);
                        if (message.orderType() == OrderType.IN_STOCK) {
                            InStockCarOrder inStockCarOrder = inStockCarOrderRepository.findById(event.getAggregateId());
                            inStockCarOrder.setState(new InStockCarOrderCancelled());
                            inStockCarOrderRepository.save(inStockCarOrder);
                        } else {
                            ComplectationCarOrder complectationCarOrder = complectationCarOrderRepository.findById(event.getAggregateId());
                            complectationCarOrder.setState(new ComplectationCarOrderCancelled());
                            complectationCarOrderRepository.save(complectationCarOrder);
                        }
                    }
                }
            }
        }
    }
}
