package ru.nsu.fit.directors.orderservice.event;

public record OrderNotificationEvent(String message, Long userId) {
}
