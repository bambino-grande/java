package ru.pogosian.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.pogosian.business.repositories.AssemblyOrderRepository;
import ru.pogosian.business.repositories.ProcessedEventRepository;
import ru.pogosian.business.services.AssemblyOrderService;
import ru.pogosian.config.RabbitMQConfig;
import ru.pogosian.messaging.OrderType;
import ru.pogosian.messaging.events.OrderSentForApproval;
import ru.pogosian.messaging.events.OrderRejected;
import ru.pogosian.presentation.DTO.response.AssemblyOrderResponse;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class StorageEventConsumer {
    private final ProcessedEventRepository processedEventRepository;
    private final AssemblyOrderService assemblyOrderService;

    @RabbitListener(queues = RabbitMQConfig.ORDER_SENT_FOR_APPROVAL_QUEUE)
    @Transactional
    public void handleOrderSentForAproval(byte[] message) throws IOException {
        OrderSentForApproval orderSentForApproval = new ObjectMapper().readValue(message, OrderSentForApproval.class);
        try (MDC.MDCCloseable ignored = MDC.putCloseable("traceId", orderSentForApproval.traceId().toString())) {
            log.info("Received order sent for approval: orderId={};", orderSentForApproval.orderId());
            if (processedEventRepository.existsByEventId(orderSentForApproval.messageId()))
                return;
            assemblyOrderService.processOrderSentForApproval(orderSentForApproval);
            processedEventRepository.save(orderSentForApproval.messageId());
        }
    }
}
