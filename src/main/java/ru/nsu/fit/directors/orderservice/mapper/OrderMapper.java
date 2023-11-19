package ru.nsu.fit.directors.orderservice.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.orderservice.dto.response.ActionDto;
import ru.nsu.fit.directors.orderservice.dto.response.EstablishmentDto;
import ru.nsu.fit.directors.orderservice.dto.response.EstablishmentResponseOrderDto;
import ru.nsu.fit.directors.orderservice.dto.response.UserResponseOrderDto;
import ru.nsu.fit.directors.orderservice.enums.BusinessAction;
import ru.nsu.fit.directors.orderservice.enums.OrderStatus;
import ru.nsu.fit.directors.orderservice.enums.UserAction;
import ru.nsu.fit.directors.orderservice.event.OrderCreatedEvent;
import ru.nsu.fit.directors.orderservice.model.Order;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Component
public class OrderMapper {

    public Order toEntity(OrderCreatedEvent dto) {
        final int bookingDurationTimeMinutes = 240;
        return new Order().setGuestName(dto.getGuestName())
            .setStatus(OrderStatus.WAITING)
            .setDate(Date.valueOf(dto.getDate()))
            .setStartTime(Time.valueOf(dto.getTime()))
            .setEndTime(Time.valueOf(dto.getTime().plusMinutes(bookingDurationTimeMinutes)))
            .setGuestCount(dto.getGuestCount())
            .setSpotId(dto.getSpotId())
            .setGuestId(dto.getUserId())
            .setEstablishmentId(dto.getEstablishmentId());
    }

    public UserResponseOrderDto toUserResponse(Order order, EstablishmentDto establishmentDto) {
        return new UserResponseOrderDto().setId(order.getId())
            .setStatus(order.getStatus().getStatus())
            .setGuestCount(order.getGuestCount())
            .setEstablishmentId(order.getEstablishmentId())
            .setDate(order.getDate())
            .setGuestName(order.getGuestName())
            .setStartTime(order.getStartTime())
            .setEndTime(order.getEndTime())
            .setName(establishmentDto.name())
            .setCuisineCountry(establishmentDto.cuisineCountry())
            .setRating(establishmentDto.rating())
            .setImage(establishmentDto.image())
            .setUserActionList(toUserActionDto(order.getStatus().getUserActions()));
    }

    public EstablishmentResponseOrderDto toEstablishmentResponse(Order order) {
        return new EstablishmentResponseOrderDto().setId(order.getId())
            .setStatus(order.getStatus().getStatus())
            .setGuestCount(order.getGuestCount())
            .setDate(order.getDate())
            .setGuestName(order.getGuestName())
            .setStartTime(order.getStartTime())
            .setEndTime(order.getEndTime())
            .setBusinessActions(toBusinessActionDto(order.getStatus().getBusinessActions()));
    }

    private List<ActionDto> toBusinessActionDto(List<BusinessAction> actions) {
        return actions.stream()
            .map(action -> new ActionDto(action.getActionName(), action.getNextStatus()))
            .toList();
    }

    private List<ActionDto> toUserActionDto(List<UserAction> actions) {
        return actions.stream()
            .map(action -> new ActionDto(action.getActionName(), action.getNextStatus()))
            .toList();
    }
}
