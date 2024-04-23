package ru.nsu.fit.directors.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.orderservice.dto.response.EstablishmentResponseOrderDto;
import ru.nsu.fit.directors.orderservice.dto.response.MessageDto;
import ru.nsu.fit.directors.orderservice.dto.response.UserResponseOrderDto;
import ru.nsu.fit.directors.orderservice.service.MessageService;
import ru.nsu.fit.directors.orderservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping(value = "order", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MainController {
    private final OrderService orderService;
    private final MessageService messageService;

    @GetMapping("/id")
    public UserResponseOrderDto getById(@RequestParam Long id) {
        return orderService.getById(id);
    }

    @GetMapping
    public List<UserResponseOrderDto> getByUser(
        @RequestParam Long userId,
        @RequestParam(required = false) Integer status
    ) {
        return orderService.getUserOrders(status, userId);
    }

    @GetMapping(value = "/establishment")
    public List<EstablishmentResponseOrderDto> getByEstablishment(
        @RequestParam Long establishmentId,
        @RequestParam(required = false) Integer status
    ) {
        return orderService.getEstablishmentOrders(establishmentId, status);
    }

    @GetMapping(value = "/message/user")
    public List<MessageDto> getMessagesByUser(@RequestParam Long userId, @RequestParam Long orderId) {
        return messageService.getByUser(orderId, userId);
    }

    @GetMapping(value = "/message/business")
    public List<MessageDto> getMessageByBusiness(@RequestParam Long businessId, @RequestParam Long orderId) {
        return messageService.getByBusiness(orderId, businessId);
    }

}
