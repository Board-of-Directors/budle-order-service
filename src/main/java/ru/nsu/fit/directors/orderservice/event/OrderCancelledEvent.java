package ru.nsu.fit.directors.orderservice.event;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class OrderCancelledEvent {
    private long orderId;
}
