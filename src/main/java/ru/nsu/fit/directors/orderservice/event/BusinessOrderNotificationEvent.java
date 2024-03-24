package ru.nsu.fit.directors.orderservice.event;

public record BusinessOrderNotificationEvent(Long businessId, Long orderId, String message) {
}
