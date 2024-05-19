package ru.nsu.fit.directors.orderservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserAction {
    CANCEL("Отменить бронь", 4),
    CONFIRM("Подтвердить бронь", 5),
    ;

    private final String actionName;
    private final Integer nextStatus;
}
