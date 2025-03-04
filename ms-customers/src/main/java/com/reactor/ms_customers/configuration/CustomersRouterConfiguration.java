package com.reactor.ms_customers.configuration;

import com.reactor.ms_customers.handler.CustomersHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.reactor.ms_customers.domain.constants.UriConstants.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class CustomersRouterConfiguration {

    @RouterOperations({
            @RouterOperation(
                    path = GET_CUSTOMER_BY_ID,
                    beanClass = CustomersHandler.class,
                    beanMethod = "getCustomerById",
                    operation = @Operation(
                            summary = "Obtener cliente por ID",
                            description = "Devuelve un cliente según su ID",
                            parameters = {
                                    @Parameter(
                                            name = "idCustomer",
                                            in = ParameterIn.QUERY,
                                            description = "El ID del cliente",
                                            required = true,
                                            example = "123"
                                    )
                            }
                    )
            ),

    })

    @Bean
    public RouterFunction<ServerResponse> customerRouter(CustomersHandler customerHandler) {
        return RouterFunctions.route(POST(ADD_CUSTOMER), customerHandler::createCustomer)
                .andRoute(GET(GET_ALL_CUSTOMERS), customerHandler::getAllCustomers)
                .andRoute(GET(GET_CUSTOMER_BY_ID).and(queryParam("idCustomer", id -> true)), customerHandler::getCustomerById);
    }
}

