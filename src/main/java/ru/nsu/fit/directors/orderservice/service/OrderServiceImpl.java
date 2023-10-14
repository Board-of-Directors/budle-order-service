package ru.nsu.fit.directors.orderservice.service;

import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.orderservice.OrderRepository;
import ru.nsu.fit.directors.orderservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.orderservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.orderservice.enums.OrderStatus;
import ru.nsu.fit.directors.orderservice.exception.NotEnoughRightsException;
import ru.nsu.fit.directors.orderservice.exception.OrderNotFoundException;
import ru.nsu.fit.directors.orderservice.mapper.OrderMapper;
import ru.nsu.fit.directors.orderservice.model.Order;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public void createOrder(RequestOrderDto dto, Long userId, Long establishmentId) {
        log.info("Creating order {}", dto);
        Order order = orderMapper.toEntity(dto)
            .setGuestId(userId)
            .setEstablishmentId(establishmentId);
        orderRepository.save(order);
    }


    @Override
    public List<ResponseOrderDto> getUserOrders(Integer status, Long userId) {
        log.info("Getting user orders");
        OrderStatus orderStatus = status == null ? null : OrderStatus.getStatusByInteger(status);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Example<Order> exampleQuery = Example.of(new Order(userId, orderStatus), matcher);
        List<Order> orders = orderRepository.findAll(exampleQuery);
        log.info("Result: " + orders);
        return orders.stream()
            .map(orderMapper::toResponse)
            .toList();
    }

    @Override
    @Nonnull
    public List<ResponseOrderDto> getEstablishmentOrders(Long establishmentId, Integer status) {
        log.info("Getting orders for establishment {}", establishmentId);
        return orderRepository.findAllByEstablishmentId(establishmentId).stream()
            .map(orderMapper::toResponse)
            .toList();
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId, Long userId) {
        log.info("Deleting order {} by user {}", orderId, userId);
        Order order = getOrderById(orderId);

        if (order.getGuestId().equals(userId)) {
            orderRepository.delete(order);
        } else {
            log.warn("Not enough right for this operation");
            throw new NotEnoughRightsException();
        }
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

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

}
