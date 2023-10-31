package ru.nsu.fit.directors.orderservice.enums;

import lombok.Getter;
import ru.nsu.fit.directors.orderservice.exception.IncorrectOrderStatusException;

import java.util.Objects;

@Getter
public enum OrderStatus {
    WAITING(0, "В ожидании"),
    ACCEPTED(1, "Подтверждена"),
    REJECTED(2, "Отклонена"),
    CANCELLED(3, "Отменена");

    private final Integer status;
    private final String message;

    OrderStatus(int status, String message) {
        this.status = status;
        this.message = message;
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
