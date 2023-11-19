package ru.nsu.fit.directors.orderservice.service;

import jakarta.transaction.Transactional;
import ru.nsu.fit.directors.orderservice.dto.response.EstablishmentResponseOrderDto;
import ru.nsu.fit.directors.orderservice.dto.response.UserResponseOrderDto;
import ru.nsu.fit.directors.orderservice.event.OrderCreatedEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * Service, that responsible for orders.
 */
@ParametersAreNonnullByDefault
public interface OrderService {
    /**
     * Creating new order by provided order dto.
     *
     * @param event contains information about new order.
     */
    void createOrder(OrderCreatedEvent event, Long userId, Long establishmentId);

    /**
     * Getting all the orders with provided parameters.
     * if false - with establishment.
     *
     * @param userId for what user we are searching orders
     * @param status - with what status we are searching orders
     * @return list of order dto
     */

    List<UserResponseOrderDto> getUserOrders(Integer status, Long userId);

    /**
     * Getting all the orders of the establishment with provided parameters.
     *
     * @param establishmentId - for what establishment we are searching orders
     * @param status          - with what status we're searching orders
     * @return list of the orders
     */

    List<EstablishmentResponseOrderDto> getEstablishmentOrders(Long establishmentId, Integer status);

    /**
     * Setting the status of the current order.
     *
     * @param orderId         what order we need to change status
     * @param establishmentId from what establishment was this request
     * @param status          new status of the order
     */
    void setStatus(Long orderId, Long establishmentId, Integer status);

    /**
     * Setting the status of the current order.
     *
     * @param orderId what order we need to accept
     * @param status  new status of order
     */

    @Transactional
    void setStatus(Long orderId, Integer status);

    UserResponseOrderDto getById(Long id);
}
