package ru.nsu.fit.directors.orderservice.service;

import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.orderservice.api.EstablishmentApi;
import ru.nsu.fit.directors.orderservice.dto.response.EstablishmentDto;
import ru.nsu.fit.directors.orderservice.dto.response.EstablishmentResponseOrderDto;
import ru.nsu.fit.directors.orderservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.orderservice.enums.OrderStatus;
import ru.nsu.fit.directors.orderservice.event.OrderCreatedEvent;
import ru.nsu.fit.directors.orderservice.event.OrderNotificationEvent;
import ru.nsu.fit.directors.orderservice.exception.OrderNotFoundException;
import ru.nsu.fit.directors.orderservice.mapper.OrderMapper;
import ru.nsu.fit.directors.orderservice.model.Order;
import ru.nsu.fit.directors.orderservice.repository.OrderRepository;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@ParametersAreNonnullByDefault
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final KafkaTemplate<String, OrderNotificationEvent> notificationKafka;
    private final EstablishmentApi establishmentApi;

    @Override
    public void createOrder(OrderCreatedEvent event, Long userId, Long establishmentId) {
        log.info("Creating order {}", event);
        Order order = orderMapper.toEntity(event)
            .setGuestId(userId)
            .setEstablishmentId(establishmentId);
        orderRepository.save(order);
    }


    @Override
    @Nonnull
    public List<ResponseOrderDto> getUserOrders(@Nullable Integer status, Long userId) {
        log.info("Getting orders for user {} with status {}", userId, status);
        OrderStatus orderStatus = status == null ? null : OrderStatus.getStatusByInteger(status);
        return orderRepository.findAllByUserAndStatus(userId, orderStatus)
            .stream()
            .map(order -> orderMapper.toUserResponse(order, getEstablishmentById(order.getEstablishmentId())))
            .toList();
    }

    private EstablishmentDto getEstablishmentById(Long establishmentId) {
        return establishmentApi.syncGetWithParams(
            uriBuilder -> uriBuilder.path("/establishment").queryParam("establishmentId", establishmentId).build(),
            new ParameterizedTypeReference<>(){}
        );
    }

    @Override
    @Nonnull
    public List<EstablishmentResponseOrderDto> getEstablishmentOrders(Long establishmentId, @Nullable Integer status) {
        log.info("Getting orders for establishment {} with status {}", establishmentId, status);
        OrderStatus orderStatus = status == null ? null : OrderStatus.getStatusByInteger(status);
        return orderRepository.findAllByEstablishmentAndStatus(establishmentId, orderStatus)
            .stream()
            .map(orderMapper::toEstablishmentResponse)
            .toList();
    }

    @Override
    @Transactional
    public void setStatus(Long orderId, Long establishmentId, Integer status) {
        log.info("Setting order {} status to {}", orderId, status);
        Order order = getOrderById(orderId);
        if (!Objects.equals(order.getEstablishmentId(), establishmentId)) {
            return;
        }
        OrderStatus orderStatus = OrderStatus.getStatusByInteger(status);
        if (orderStatus.isAllowedByEstablishment() && orderStatus.getAllowedFrom().contains(order.getStatus())) {
            orderRepository.save(order.setStatus(orderStatus));
            notificationKafka.send(
                "notificationTopic",
                new OrderNotificationEvent(
                    orderStatus.getNotification(),
                    order.getGuestId()
                )
            );
        }
    }

    @Override
    @Transactional
    public void setStatus(Long orderId, Integer status) {
        log.info("Setting order {} status to {}", orderId, status);
        Order order = getOrderById(orderId);
        OrderStatus nextStatus = OrderStatus.getStatusByInteger(status);
        if (nextStatus.isAllowedByUser() && nextStatus.getAllowedFrom().contains(order.getStatus())) {
            orderRepository.save(order.setStatus(OrderStatus.getStatusByInteger(status)));
        }
    }

    @Nonnull
    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

}
