package ru.nsu.fit.directors.orderservice.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import ru.nsu.fit.directors.orderservice.enums.SenderType;

@Builder
public record MessageDto(String message, LocalDateTime timestamp, SenderType senderType) {
}
