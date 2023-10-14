package ru.nsu.fit.directors.orderservice.service;

import jakarta.transaction.Transactional;
import ru.nsu.fit.directors.orderservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.orderservice.dto.response.ResponseOrderDto;

import java.util.List;

/**
 * Service, that responsible for orders.
 */
public interface OrderService {
    /**
     * Creating new order by provided order dto.
     *
     * @param dto contains information about new order.
     */
    void createOrder(RequestOrderDto dto, Long userId, Long establishmentId);

    /**
     * Getting all the orders with provided parameters.
     * if false - with establishment.
     *
     * @param status - with what status we're searching orders.
     * @return list of order dto.
     */

    List<ResponseOrderDto> getUserOrders(Integer status, Long userId);

    List<ResponseOrderDto> getEstablishmentOrders(Long establishmentId, Integer status);

    /**
     * @param orderId what order we need to delete.
     * @param id      of provided entity.
     */
    void deleteOrder(Long orderId, Long id);

    /**
     * @param orderId         what order we need to accept.
     * @param establishmentId from what establishment was this request.
     */
    void setStatus(Long orderId, Long establishmentId, Integer status);

    @Transactional
    void setStatus(Long orderId, Integer status);
}
