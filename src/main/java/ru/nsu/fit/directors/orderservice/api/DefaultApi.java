package ru.nsu.fit.directors.orderservice.api;

import brave.internal.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import ru.nsu.fit.directors.orderservice.dto.response.BaseResponse;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

public interface DefaultApi {

    @Nullable
    <T> T syncGetWithParams(Function<UriBuilder, URI> uriBuilder, ParameterizedTypeReference<BaseResponse<T>> reference);

    @Nullable
    <T> List<T> syncListGetWithParams(Function<UriBuilder, URI> uriBuilder, ParameterizedTypeReference<BaseResponse<List<T>>> reference);

    <R, B> R syncPostRequestWithBody(
        Function<UriBuilder, URI> uriBuilder,
        ParameterizedTypeReference<BaseResponse<R>> resultReference,
        Mono<B> body,
        ParameterizedTypeReference<B> bodyReference
    );

}
