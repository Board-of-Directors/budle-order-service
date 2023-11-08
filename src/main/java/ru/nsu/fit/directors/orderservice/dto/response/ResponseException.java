package ru.nsu.fit.directors.orderservice.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class, that used for performing exception in the web response.
 * Contains error message and error type.
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseException {
    private String message;
    private String type;

    /**
     * Default constructor of exception.
     *
     * @param message of the error.
     * @param type    of the error.
     */
    public ResponseException(String message, String type) {
        this.message = message;
        this.type = type;
    }

}
