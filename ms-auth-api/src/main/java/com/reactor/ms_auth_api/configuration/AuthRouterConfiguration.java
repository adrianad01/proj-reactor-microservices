package com.reactor.ms_auth_api.configuration;

import com.reactor.ms_auth_api.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.reactor.ms_auth_api.domain.constant.UriConstants.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class AuthRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> authRoutes(AuthHandler authHandler) {
        System.out.println("🔹 Registrando rutas de autenticación...");

        return RouterFunctions.route(POST(AUTH_LOGIN), authHandler::login)
                .andRoute(GET(AUTH_VALIDATE), authHandler::validateToken)
                .andRoute(GET(PROTECTED_ROUTE), authHandler::protectedResource);
    }
}
