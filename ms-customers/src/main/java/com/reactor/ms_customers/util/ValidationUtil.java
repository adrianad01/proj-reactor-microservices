package com.reactor.ms_customers.util;

import com.reactor.ms_customers.domain.dto.CustomerDTO;
import com.reactor.ms_customers.domain.exception.CustomException;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import static com.reactor.ms_customers.domain.constants.MessageConstants.NOMBRE_CLIENTE_OBLIGATORIO;
import static com.reactor.ms_customers.domain.constants.MessageConstants.PAIS_OBLIGATORIO;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static Mono<Void> validateCustomerDTO(CustomerDTO customerDTO) {
        if (customerDTO.getNombre() == null || customerDTO.getNombre().trim().isEmpty()) {
            return Mono.error(new CustomException(NOMBRE_CLIENTE_OBLIGATORIO, HttpStatus.BAD_REQUEST.value()));
        }
        if (customerDTO.getIdPais() == null) {
            return Mono.error(new CustomException(PAIS_OBLIGATORIO, HttpStatus.BAD_REQUEST.value()));
        }
        return Mono.empty();
    }
}
