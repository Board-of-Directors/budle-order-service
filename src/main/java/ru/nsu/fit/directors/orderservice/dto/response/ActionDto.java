package ru.nsu.fit.directors.orderservice.dto.response;

public record ActionDto(
    String actionName,
    Integer nextStatus
) {
}
