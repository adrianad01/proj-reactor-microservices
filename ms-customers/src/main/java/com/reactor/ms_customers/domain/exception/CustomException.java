package com.reactor.ms_customers.domain.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class CustomException extends RuntimeException{

    private final String message;
    private final int status;
    private final String traceId;

    public CustomException(String message, int status) {
        this(message, status, "");
    }
}
