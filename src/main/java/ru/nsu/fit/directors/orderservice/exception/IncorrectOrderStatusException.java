package ru.nsu.fit.directors.orderservice.exception;

public class IncorrectOrderStatusException extends BaseException {
    public IncorrectOrderStatusException() {
        super("Был передан неправильный статус заказа", "IncorrectOrderStatusException");
    }
}
