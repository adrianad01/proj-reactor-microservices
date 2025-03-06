package com.reactor.ms_countries.configuration;

import com.reactor.ms_countries.handler.CountryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.reactor.ms_countries.domain.constant.UriConstant.COUNTRY_URI_GET_ALL;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class CountryConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routerCountry(CountryHandler countryHandler) {
        return RouterFunctions.route(GET(COUNTRY_URI_GET_ALL), request -> countryHandler.getAllCustomers());
    }
}
