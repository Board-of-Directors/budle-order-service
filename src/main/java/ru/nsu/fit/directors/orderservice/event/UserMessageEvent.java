package ru.nsu.fit.directors.orderservice.event;

public record UserMessageEvent(Long userId, Long orderId, String message) {
}
