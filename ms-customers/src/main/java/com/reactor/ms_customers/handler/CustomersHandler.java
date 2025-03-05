package com.reactor.ms_customers.handler;

import com.reactor.ms_customers.domain.dto.CustomerDTO;
import com.reactor.ms_customers.domain.exception.CustomException;
import com.reactor.ms_customers.service.CustomersService;
import com.reactor.ms_customers.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomersHandler {

    private final CustomersService customersService;

    public Mono<ServerResponse> createCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerDTO.class)
                .defaultIfEmpty(new CustomerDTO())
                .flatMap(customerDTO -> ValidationUtil.validateCustomerDTO(customerDTO)
                        .then(customersService.createCustomer(customerDTO)))
                .flatMap(response -> {
                    if (!response.isSuccessful()) {
                        return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).bodyValue(response);
                    }
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response);
                })
                .onErrorResume(CustomException.class, e ->
                        ServerResponse.status(e.getStatus()).contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(e.getMessage())
                );
    }

    public Mono<ServerResponse> getAllCustomers() {
        return customersService.getAllCustomers()
                .collectList()
                .flatMap(customers -> {
                    if (customers.isEmpty()) {
                        return ServerResponse.noContent().build();
                    }
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(customers);
                })
                .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("Error al obtener la lista de clientes: " + e.getMessage()));
    }


    public Mono<ServerResponse> getCustomerById(ServerRequest request) {
        return Mono.defer(() -> {
            String idParam = request.pathVariable("idCustomer");
            try {
                int id = Integer.parseInt(idParam);
                return customersService.getCustomerById(id)
                        .flatMap(customer -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON).bodyValue(customer))
                        .switchIfEmpty(ServerResponse.notFound().build());
            } catch (NumberFormatException e) {
                return ServerResponse.badRequest().bodyValue("Invalid 'idCustomer': must be an integer");
            }
        });
    }


    public Mono<ServerResponse> updateCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerDTO.class)
                .flatMap(customerDTO -> {
                    if (customerDTO.getIdCliente() == null) {
                        return ServerResponse.badRequest().bodyValue("El ID del cliente es obligatorio para actualizar");
                    }
                    return customersService.updateCustomer(customerDTO)
                            .flatMap(response -> {
                                if (!response.isSuccessful()) {
                                    return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(response);
                                }
                                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response);
                            })
                            .onErrorResume(error -> ServerResponse.status(500)
                                    .bodyValue("Error interno al actualizar el cliente: " + error.getMessage()));
                })
                .switchIfEmpty(ServerResponse.badRequest().bodyValue("El cuerpo de la solicitud no puede estar vacío"));
    }
}
