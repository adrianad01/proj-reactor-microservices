package com.reactor.ms_customers.domain.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class CustomException extends RuntimeException{

    private final String message;
    private final int status;
    private final String traceId;

    public CustomException(String message, int status) {
        this(message, status, "");
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return this.status;
    }

    public String getTraceId() {
        return this.traceId;
    }

    public CustomException(String message, int status, String traceId) {
        this.message = message;
        this.status = status;
        this.traceId = traceId;
    }
}
