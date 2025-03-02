package com.reactor.ms_customers.domain.responses;

import java.util.Date;

public record ErrorResponse(String message, String errorPath, int statusCode, Date timestamp)  {
}
