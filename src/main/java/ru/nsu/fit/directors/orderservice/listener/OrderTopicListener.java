package ru.nsu.fit.directors.orderservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.orderservice.enums.OrderStatus;
import ru.nsu.fit.directors.orderservice.event.BusinessMessageEvent;
import ru.nsu.fit.directors.orderservice.event.OrderCancelledEvent;
import ru.nsu.fit.directors.orderservice.event.OrderConfirmedEvent;
import ru.nsu.fit.directors.orderservice.event.OrderCreatedEvent;
import ru.nsu.fit.directors.orderservice.event.OrderStatusChangedEvent;
import ru.nsu.fit.directors.orderservice.event.UserMessageEvent;
import ru.nsu.fit.directors.orderservice.service.MessageService;
import ru.nsu.fit.directors.orderservice.service.OrderService;

import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
@KafkaListener(topics = "orderTopic")
public class OrderTopicListener {
    private final OrderService orderService;
    private final MessageService messageService;

    @KafkaHandler
    void handleBusinessMessage(BusinessMessageEvent businessMessageEvent) {
        log.info("Received business message event {}", businessMessageEvent);
        messageService.save(businessMessageEvent);
    }

    @KafkaHandler
    void handleUserMessage(UserMessageEvent userMessageEvent) {
        log.info("Received user message event {}", userMessageEvent);
        messageService.save(userMessageEvent);
    }

    @KafkaHandler
    void handleOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        orderService.createOrder(
            orderCreatedEvent,
            orderCreatedEvent.getUserId(),
            orderCreatedEvent.getEstablishmentId()
        );
    }

    @KafkaHandler
    void handleOrderCancelled(OrderCancelledEvent event) {
        orderService.setStatus(event.getOrderId(), OrderStatus.CANCELLED.getStatus());
    }

    @KafkaHandler
    void handleOrderConfirmed(OrderConfirmedEvent event) {
        orderService.setStatus(event.orderId(), OrderStatus.CONFIRMED.getStatus());
    }

    @KafkaHandler
    void handleOrderStatusChanged(OrderStatusChangedEvent event) {
        orderService.setStatus(
            event.getOrderId(),
            event.getEstablishmentId(),
            event.getStatus()
        );
    }
}
