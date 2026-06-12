package ru.pogosian.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "RabbitMQConfig";
    public static final String ORDER_SENT_FOR_APPROVAL_QUEUE = "order.sent-for-approval.queue";
    public static final String ORDER_APPROVED_QUEUE = "order.approved.queue";
    public static final String ORDER_REJECTED_QUEUE = "order.rejected.queue";
    public static final String ROUTING_KEY_SENT_FOR_APPROVAL = "order.sent-for-approval";
    public static final String ROUTING_KEY_APPROVED = "order.approved";
    public static final String ROUTING_KEY_REJECTED = "order.rejected";



    @Bean
    public TopicExchange dealershipExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue orderSentForApprovalQueue() {
        return QueueBuilder.durable(ORDER_SENT_FOR_APPROVAL_QUEUE).build();
    }

    @Bean
    public Queue orderApprovedQueue() {
        return QueueBuilder.durable(ORDER_APPROVED_QUEUE).build();
    }

    @Bean
    public Queue orderRejectedQueue() {
        return QueueBuilder.durable(ORDER_REJECTED_QUEUE).build();
    }

    @Bean
    public Binding orderSentForApprovalBinding (@Qualifier("orderSentForApprovalQueue") Queue orderSentForApprovalQueue, TopicExchange dealershipExchange) {
        return BindingBuilder.bind(orderSentForApprovalQueue).to(dealershipExchange).with(ROUTING_KEY_SENT_FOR_APPROVAL);
    }

    @Bean
    public Binding orderApprovedBinding (@Qualifier("orderApprovedQueue") Queue orderApprovedQueue, TopicExchange dealershipExchange) {
        return BindingBuilder.bind(orderApprovedQueue).to(dealershipExchange).with(ROUTING_KEY_APPROVED);
    }

    @Bean
    public Binding orderRejectedBinding (@Qualifier("orderRejectedQueue") Queue orderRejectedQueue, TopicExchange dealershipExchange) {
        return BindingBuilder.bind(orderRejectedQueue).to(dealershipExchange).with(ROUTING_KEY_REJECTED);
    }
}
