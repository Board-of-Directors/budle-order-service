package ru.nsu.fit.directors.orderservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessAction {
    ACCEPT("Одобрить бронь", 1),
    REJECT("Отклонить бронь", 2),
    REQUEST_CONFIRMATION("Запросить подтверждение", 3),
    ;

    private final String actionName;
    private final Integer nextStatus;

}
