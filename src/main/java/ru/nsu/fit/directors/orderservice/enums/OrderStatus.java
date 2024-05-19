package ru.nsu.fit.directors.orderservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.nsu.fit.directors.orderservice.exception.IncorrectOrderStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    WAITING(
        0,
        "В ожидании",
        Collections.emptyList(),
        "Пользователь %s создал бронь в вашем заведении.",
        false,
        true,
        List.of(UserAction.CANCEL),
        List.of(BusinessAction.ACCEPT, BusinessAction.REJECT)
    ),
    ACCEPTED(
        1,
        "Одобрена",
        List.of(WAITING),
        "Администратор одобрил вашу бронь.",
        true,
        false,
        List.of(UserAction.CANCEL),
        List.of(BusinessAction.REQUEST_CONFIRMATION, BusinessAction.REJECT)
    ),
    REJECTED(
        2,
        "Отклонена",
        List.of(WAITING),
        "Администратор отклонил вашу бронь.",
        true,
        false,
        Collections.emptyList(),
        List.of(BusinessAction.ACCEPT)
    ),
    WAITING_FOR_CONFIRMATION(
        3,
        "Ждет подтверждения от пользователя",
        List.of(ACCEPTED),
        "Администратор запросил подтверждение вашей брони. Подтвердите, что точно придете.",
        true,
        false,
        List.of(UserAction.CANCEL, UserAction.CONFIRM),
        Collections.emptyList()
    ),
    CANCELLED(
        4,
        "Отменена",
        List.of(WAITING, ACCEPTED, WAITING_FOR_CONFIRMATION),
        "Пользователь %s отменил свою бронь.",
        false,
        true,
        Collections.emptyList(),
        Collections.emptyList()
    ),
    CONFIRMED(
        5,
        "Подтверждена",
        List.of(WAITING_FOR_CONFIRMATION),
        "Пользователь %s подтвердил, что точно придет.",
        false,
        true,
        Collections.emptyList(),
        Collections.emptyList()
    ),
    ARCHIVED(
        6,
        "В архиве",
        Collections.emptyList(),
        "Бронь была помещена в архив.",
        false,
        true,
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

    @Nonnull
    public static OrderStatus getStatusByInteger(Integer status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (Objects.equals(orderStatus.status, status)) {
                return orderStatus;
            }
        }
        throw new IncorrectOrderStatusException();
    }
}
