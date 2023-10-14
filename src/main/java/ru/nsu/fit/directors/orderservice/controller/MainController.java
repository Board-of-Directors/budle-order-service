package ru.nsu.fit.directors.orderservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.orderservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.orderservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.orderservice.service.OrderService;

import java.util.List;

/**
 * Class, that represents order controller.
 */
@RestController
@RequestMapping(value = "order", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MainController {
    private final OrderService orderService;

    /**
     * Post request, that creates order for current establishment.
     *
     * @param request - order dto represent order details, such as time, date, etc.
     *                For details of order dto you can watch OrderDTO class.
     */
    @PostMapping
    public void create(
        @RequestBody @Valid RequestOrderDto request,
        Long userId,
        Long establishmentId
    ) {
        orderService.createOrder(request, userId, establishmentId);
    }

    /**
     * Delete request, that must be sent by current user.
     * Delete his order from our database.
     *
     * @param orderId what order we need to delete.
     * @param userId  from what user we create request (will be deleted)
     */
    @DeleteMapping
    public void delete(
        @RequestParam Long orderId,
        @RequestParam Long userId
    ) {
        orderService.deleteOrder(orderId, userId);
    }

    /**
     * Get request, that return all orders of current user.
     *
     * @param userId - from what user we need to find orders.
     * @param status - with what status we need to find orders.
     * @return list of order dto.
     */
    @GetMapping
    public List<ResponseOrderDto> getByUser(
        @RequestParam Long userId,
        @RequestParam(required = false) Integer status
    ) {
        return orderService.getUserOrders(status, userId);
    }

    @GetMapping(value = "/establishment")
    public List<ResponseOrderDto> getByEstablishment(
        @RequestParam Long establishmentId,
        @RequestParam(required = false) Integer status
    ) {
        return orderService.getEstablishmentOrders(establishmentId, status);
    }

}
