package ru.nsu.fit.directors.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
     * Internal get request, that return all orders of current user.
     *
     * @param userId - from what user we need to find orders
     * @param status - with what status we need to find orders
     * @return list of order dto
     */
    @GetMapping
    public List<ResponseOrderDto> getByUser(
        @RequestParam Long userId,
        @RequestParam(required = false) Integer status
    ) {
        return orderService.getUserOrders(status, userId);
    }

    /**
     * Internal get request, that return all orders of the current establishment.
     *
     * @param establishmentId identifier of establishment that we fetch orders for
     * @param status          (optional) what orders statuses we expected
     * @return list of orders
     */

    @GetMapping(value = "/establishment")
    public List<ResponseOrderDto> getByEstablishment(
        @RequestParam Long establishmentId,
        @RequestParam(required = false) Integer status
    ) {
        return orderService.getEstablishmentOrders(establishmentId, status);
    }

}
