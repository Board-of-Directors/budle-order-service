package ru.nsu.fit.directors.orderservice.enums;

import lombok.Getter;

@Getter
public enum UserAction {
    CANCEL("Отменить бронь", 4), CONFIRM("Подтвердить бронь", 5);

    private final String actionName;
    private final Integer nextStatus;


    UserAction(String actionName, Integer nextStatus) {
        this.actionName = actionName;
        this.nextStatus = nextStatus;
    }
}
