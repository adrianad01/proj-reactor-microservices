package com.reactor.ms_customers.handler;

import com.reactor.ms_customers.domain.dto.CustomerDTO;
import com.reactor.ms_customers.domain.exception.CustomException;
import com.reactor.ms_customers.service.CustomersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.reactor.ms_customers.domain.constants.MessageConstants.*;

@Component
@RequiredArgsConstructor
public class CustomersHandler {

    private final CustomersService customersService;

    public Mono<ServerResponse> createCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerDTO.class)
                .flatMap(customerDTO -> {
                    if (customerDTO.getNombre() == null || customerDTO.getNombre().trim().isEmpty()) {
                        return Mono.error(new CustomException(NOMBRE_CLIENTE_OBLIGATORIO, HttpStatus.BAD_REQUEST.value()));
                    }
                    if (customerDTO.getIdPais() == null) {
                        return Mono.error(new CustomException(PAIS_OBLIGATORIO, HttpStatus.BAD_REQUEST.value()));
                    }
                    return customersService.createCustomer(customerDTO)
                            .flatMap(response -> {
                                if (!response.isSuccessful()) {
                                    return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).bodyValue(response);
                                }
                                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response);
                            });
                })
                .switchIfEmpty(Mono.error(new CustomException(REQUEST_INVALIDO, HttpStatus.BAD_REQUEST.value())));
    }

    public Mono<ServerResponse> getAllCustomers(ServerRequest request) {
        return customersService.getAllCustomers()
                .collectList()
                .flatMap(customers -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(customers))
                .switchIfEmpty(ServerResponse.noContent().build());
    }

}
