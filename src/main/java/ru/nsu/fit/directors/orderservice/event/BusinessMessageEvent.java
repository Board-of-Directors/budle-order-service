package ru.nsu.fit.directors.orderservice.event;

public record BusinessMessageEvent(Long businessId, Long orderId, String message) {
}
