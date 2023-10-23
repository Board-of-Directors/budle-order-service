package ru.nsu.fit.directors.orderservice.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.nsu.fit.directors.orderservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.orderservice.exception.BaseException;
import ru.nsu.fit.directors.orderservice.exception.IncorrectOrderStatusException;
import ru.nsu.fit.directors.orderservice.exception.NotEnoughRightsException;
import ru.nsu.fit.directors.orderservice.exception.OrderNotFoundException;

import java.util.LinkedHashMap;

@RestControllerAdvice(basePackages = "ru.nsu.fit.directors.orderservice.controller")
public class ArticleController extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<Object> {
    private static final String NOT_VALID_EXCEPTION = "notValidException";

    @ExceptionHandler({
        IncorrectOrderStatusException.class,
        NotEnoughRightsException.class,
        OrderNotFoundException.class
    })
    public <T extends BaseException> ResponseEntity<BaseResponse<Object>> handleException(T e) {
        BaseResponse<Object> response = new BaseResponse<>(e.getMessage(), e.getType());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    public boolean supports(
        @NonNull MethodParameter returnType,
        @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
        Object body,
        @NonNull MethodParameter returnType,
        @NonNull MediaType selectedContentType,
        @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
        @NonNull ServerHttpRequest request,
        @NonNull ServerHttpResponse response
    ) {
        if (body instanceof BaseResponse<?> || body instanceof LinkedHashMap<?, ?>) {
            return body;
        } else if (selectedContentType.toString().equals(MediaType.APPLICATION_XML_VALUE)) {
            return body;
        }
        return new BaseResponse<>(body);
    }

    @Nullable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        @NonNull MethodArgumentNotValidException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request
    ) {
        String message = getDefaultMessage(ex);
        BaseResponse<Object> response = new BaseResponse<>(message, NOT_VALID_EXCEPTION);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Nullable
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request
    ) {
        String message = ex.getMessage();
        BaseResponse<Object> response = new BaseResponse<>(message, NOT_VALID_EXCEPTION);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @NonNull
    private String getDefaultMessage(BindException ex) {
        return ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList()
            .get(0);

    }
}
