package ru.nsu.fit.directors.orderservice.api;

import brave.internal.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import ru.nsu.fit.directors.orderservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.orderservice.exception.ClientException;
import ru.nsu.fit.directors.orderservice.exception.ServerNotAvailableException;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AbstractApi implements DefaultApi {
    private final WebClient.Builder webClient;

    public AbstractApi(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    @Nullable
    public <T> T syncGetWithParams(
        Function<UriBuilder, URI> uriBuilder,
        ParameterizedTypeReference<BaseResponse<T>> reference
    ) {
        return Optional.ofNullable(webClient.build()
                .get()
                .uri(uriBuilder)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> response.bodyToMono(String.class).map(ServerNotAvailableException::new))
                .toEntity(reference)
                .block())
            .map(ResponseEntity::getBody)
            .map(BaseResponse::getResult)
            .orElse(null);
    }

    @Nullable
    public <T> List<T> syncListGetWithParams(
        Function<UriBuilder, URI> uriBuilder,
        ParameterizedTypeReference<BaseResponse<List<T>>> reference
    ) {
        return Optional.ofNullable(webClient.build()
                .get()
                .uri(uriBuilder)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                    HttpStatusCode::is4xxClientError,
                    response -> response.bodyToMono(BaseResponse.class)
                        .map(resp -> new ClientException(resp.getException().getMessage()))
                )
                .onStatus(
                    HttpStatusCode::is5xxServerError,
                    response -> response.bodyToMono(String.class).map(ServerNotAvailableException::new)
                )
                .toEntity(reference)
                .block())
            .map(ResponseEntity::getBody)
            .map(BaseResponse::getResult)
            .orElse(null);
    }

    @Override
    public <R, B> R syncPostRequestWithBody(
        Function<UriBuilder, URI> uriBuilder,
        ParameterizedTypeReference<BaseResponse<R>> resultReference,
        Mono<B> body,
        ParameterizedTypeReference<B> bodyReference
    ) {
        return webClient.build()
            .post()
            .uri(uriBuilder)
            .body(body, bodyReference)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                response -> response.bodyToMono(BaseResponse.class)
                    .map(resp -> new ClientException(resp.getException().getMessage()))
            )
            .onStatus(
                HttpStatusCode::is5xxServerError,
                response -> response.bodyToMono(String.class).map(ServerNotAvailableException::new)
            )
            .toEntity(resultReference)
            .blockOptional()
            .map(ResponseEntity::getBody)
            .map(BaseResponse::getResult)
            .orElse(null);
    }
}
