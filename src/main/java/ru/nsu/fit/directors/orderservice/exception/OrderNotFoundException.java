package ru.nsu.fit.directors.orderservice.exception;

public class OrderNotFoundException extends BaseException {
    static final private String ERROR_TYPE = "OrderNotFoundException";

    public OrderNotFoundException(Long id) {
        super("Заказ с id " + id + " не существует.", ERROR_TYPE);
    }
}
