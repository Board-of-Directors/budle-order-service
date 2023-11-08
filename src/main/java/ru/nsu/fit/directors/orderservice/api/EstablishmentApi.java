package ru.nsu.fit.directors.orderservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EstablishmentApi extends AbstractApi {

    @Autowired
    public EstablishmentApi(WebClient.Builder establishmentClientBuilder) {
        super(establishmentClientBuilder);
    }

}
