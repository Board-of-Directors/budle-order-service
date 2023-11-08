package ru.nsu.fit.directors.orderservice.exception;


public class ClientException extends BaseException {
    public ClientException(String message) {
        super(message, "ClientException");
    }
}
