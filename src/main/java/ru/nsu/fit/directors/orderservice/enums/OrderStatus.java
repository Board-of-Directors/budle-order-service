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
        false,
        List.of(UserAction.CANCEL),
        List.of(BusinessAction.ACCEPT, BusinessAction.REJECT)
    ),
    ACCEPTED(
        1,
        "Одобрена",
        List.of(WAITING),
        "Администратор одобрил вашу бронь.",
        true,
        List.of(UserAction.CANCEL),
        List.of(BusinessAction.REQUEST_CONFIRMATION, BusinessAction.REJECT)
    ),
    REJECTED(
        2,
        "Отклонена",
        List.of(WAITING),
        "Администратор отклонил вашу бронь.",
        true,
        Collections.emptyList(),
        List.of(BusinessAction.ACCEPT)
    ),
    WAITING_FOR_CONFIRMATION(
        3,
        "Ждет подтверждения от пользователя",
        List.of(ACCEPTED),
        "Администратор запросил подтверждение вашей брони. Подтвердите, что точно придете.",
        true,
        List.of(UserAction.CANCEL, UserAction.CONFIRM),
        Collections.emptyList()
    ),
    CANCELLED(
        4,
        "Отменена",
        List.of(WAITING, ACCEPTED, WAITING_FOR_CONFIRMATION),
        "Пользователь %s отменил свою бронь.",
        false,
        Collections.emptyList(),
        Collections.emptyList()
    ),
    CONFIRMED(
        5,
        "Подтверждена",
        List.of(WAITING_FOR_CONFIRMATION),
        "Пользователь %s подтвердил, что точно придет.",
        false,
        Collections.emptyList(),
        Collections.emptyList()
    ),
    ARCHIVED(
        6,
        "В архиве",
        Collections.emptyList(),
        "Бронь была помещена в архив.",
        false,
        Collections.emptyList(),
        Collections.emptyList()
    );

    private final Integer status;
    private final String message;
    private final List<OrderStatus> allowedFrom;
    private final String notification;
    private final boolean allowedByEstablishment;
    private final boolean allowedByUser;
    private final List<UserAction> userActions;
    private final List<BusinessAction> businessActions;

    OrderStatus(
        int status,
        String message,
        List<OrderStatus> allowedFrom,
        String notification,
        boolean allowedByEstablishment,
        List<UserAction> userActions,
        List<BusinessAction> businessActions
    ) {
        this.status = status;
        this.message = message;
        this.allowedFrom = allowedFrom;
        this.notification = notification;
        this.allowedByEstablishment = allowedByEstablishment;
        this.allowedByUser = !allowedByEstablishment;
        this.userActions = userActions;
        this.businessActions = businessActions;
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
