package ru.nsu.fit.directors.orderservice.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record MessageDto(String message, LocalDateTime timestamp) {
}
