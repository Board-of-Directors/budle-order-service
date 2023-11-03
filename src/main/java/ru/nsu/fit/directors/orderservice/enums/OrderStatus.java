package ru.nsu.fit.directors.orderservice.enums;

import lombok.Getter;
import ru.nsu.fit.directors.orderservice.exception.IncorrectOrderStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public enum OrderStatus {
    WAITING(
        0,
        "В ожидании",
        Collections.emptyList(),
        "Пользователь %s создал бронь в вашем заведении.",
        false
    ),
    ACCEPTED(
        1,
        "Одобрена",
        List.of(WAITING),
        "Администратор одобрил вашу бронь.",
        true
    ),
    REJECTED(
        2,
        "Отклонена",
        List.of(WAITING),
        "Администратор отклонил вашу бронь.",
        true
    ),
    WAITING_FOR_CONFIRMATION(
        3,
        "Ждет подтверждения от пользователя",
        List.of(ACCEPTED),
        "Администратор запросил подтверждение вашей брони. Подтвердите, что точно придете.",
        true
    ),
    CANCELLED(
        4,
        "Отменена",
        List.of(WAITING, ACCEPTED, WAITING_FOR_CONFIRMATION),
        "Пользователь %s отменил свою бронь.",
        false
    ),
    CONFIRMED(
        5,
        "Подтверждена",
        List.of(WAITING_FOR_CONFIRMATION),
        "Пользователь %s подтвердил, что точно придет.",
        false
    ),
    ARCHIVED(
        6,
        "В архиве",
        Collections.emptyList(),
        "Бронь была помещена в архив.",
        false
    );

    private final Integer status;
    private final String message;
    private final List<OrderStatus> allowedFrom;
    private final String notification;
    private final boolean allowedByEstablishment;
    private final boolean allowedByUser;

    OrderStatus(int status, String message, List<OrderStatus> allowedFrom, String notification, boolean allowedByEstablishment) {
        this.status = status;
        this.message = message;
        this.allowedFrom = allowedFrom;
        this.notification = notification;
        this.allowedByEstablishment = allowedByEstablishment;
        this.allowedByUser = !allowedByEstablishment;
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
