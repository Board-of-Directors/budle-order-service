package ru.nsu.fit.directors.orderservice.service;

import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.orderservice.dto.request.OrderCreatedEvent;
import ru.nsu.fit.directors.orderservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.orderservice.enums.OrderStatus;
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
            .map(orderMapper::toResponse)
            .toList();
    }

    @Override
    @Nonnull
    public List<ResponseOrderDto> getEstablishmentOrders(Long establishmentId, @Nullable Integer status) {
        log.info("Getting orders for establishment {} with status {}", establishmentId, status);
        OrderStatus orderStatus = status == null ? null : OrderStatus.getStatusByInteger(status);
        return orderRepository.findAllByEstablishmentAndStatus(establishmentId, orderStatus)
            .stream()
            .map(orderMapper::toResponse)
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
        orderRepository.save(order.setStatus(OrderStatus.getStatusByInteger(status)));
    }

    @Override
    @Transactional
    public void setStatus(Long orderId, Integer status) {
        log.info("Setting order {} status to {}", orderId, status);
        Order order = getOrderById(orderId);
        orderRepository.save(order.setStatus(OrderStatus.getStatusByInteger(status)));
    }

    @Nonnull
    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

}
