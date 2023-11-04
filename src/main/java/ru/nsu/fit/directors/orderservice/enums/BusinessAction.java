package ru.nsu.fit.directors.orderservice.enums;

import lombok.Getter;

@Getter
public enum BusinessAction {
    ACCEPT("Одобрить бронь", 1),
    REJECT("Отклонить бронь", 2),
    REQUEST_CONFIRMATION("Запросить подтверждение", 3);

    private final String actionName;
    private final Integer nextStatus;

    BusinessAction(String actionName, Integer nextStatus) {
        this.actionName = actionName;
        this.nextStatus = nextStatus;
    }

}
