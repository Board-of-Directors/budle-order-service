package ru.nsu.fit.directors.orderservice.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderStatusChangedEvent {
    private int status;
    private long establishmentId;
    private long orderId;
}
