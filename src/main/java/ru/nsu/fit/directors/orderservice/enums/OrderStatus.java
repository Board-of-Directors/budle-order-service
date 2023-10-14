package ru.nsu.fit.directors.orderservice.enums;

import lombok.Getter;
import ru.nsu.fit.directors.orderservice.exception.IncorrectOrderStatusException;

import java.util.Objects;

@Getter
public enum OrderStatus {
    WAITING(0), ACCEPTED(1), REJECTED(2), CANCELLED(3);

    private final Integer status;

    OrderStatus(int status) {
        this.status = status;
    }

    public static OrderStatus getStatusByInteger(Integer status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (Objects.equals(orderStatus.status, status)) {
                return orderStatus;
            }
        }
        throw new IncorrectOrderStatusException();
    }
}
