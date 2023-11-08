package ru.nsu.fit.directors.orderservice.exception;

public class ServerNotAvailableException extends BaseException {
    @SuppressWarnings("unused")
    public ServerNotAvailableException(String message) {
        super("Сервер в данный момент недоступен. Попробуйте позже.", "ServerNotAvailableException");
    }
}
