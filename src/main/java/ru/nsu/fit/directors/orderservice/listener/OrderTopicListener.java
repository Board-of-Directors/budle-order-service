package ru.nsu.fit.directors.orderservice.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.orderservice.dto.request.OrderCreatedEvent;
import ru.nsu.fit.directors.orderservice.enums.OrderStatus;
import ru.nsu.fit.directors.orderservice.event.OrderCancelledEvent;
import ru.nsu.fit.directors.orderservice.event.OrderStatusChangedEvent;
import ru.nsu.fit.directors.orderservice.service.OrderService;

import javax.annotation.ParametersAreNonnullByDefault;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
@KafkaListener(topics = "orderTopic")
public class OrderTopicListener {
    private final OrderService orderService;

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
    void handleOrderStatusChanged(OrderStatusChangedEvent event) {
        orderService.setStatus(
            event.getOrderId(),
            event.getEstablishmentId(),
            event.getStatus()
        );
    }
}
