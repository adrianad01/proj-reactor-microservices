package com.reactor.ms_countries.handler;

import com.reactor.ms_countries.service.CountryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CountryHandler {

    private final CountryServiceImpl countryService;

    public Mono<ServerResponse> getAllCustomers() {
        return countryService.findAllCountries()
                .collectList()
                .flatMap(country -> {
                    if (country.isEmpty()) {
                        return ServerResponse.noContent().build();
                    }
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(country);
                })
                .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("Error al obtener la lista de paises: " + e.getMessage()));
    }
}
