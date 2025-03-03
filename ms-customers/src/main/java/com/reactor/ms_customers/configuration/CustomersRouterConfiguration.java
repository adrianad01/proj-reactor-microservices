package com.reactor.ms_customers.configuration;

import com.reactor.ms_customers.handler.CustomersHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.reactor.ms_customers.domain.constants.UriConstants.ADD_CUSTOMER;
import static com.reactor.ms_customers.domain.constants.UriConstants.GET_ALL_CUSTOMERS;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class CustomersRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> customerRouter(CustomersHandler customerHandler) {
        return RouterFunctions.route(POST(ADD_CUSTOMER), customerHandler::createCustomer)
                .andRoute(GET(GET_ALL_CUSTOMERS), customerHandler::getAllCustomers);
    }
}

