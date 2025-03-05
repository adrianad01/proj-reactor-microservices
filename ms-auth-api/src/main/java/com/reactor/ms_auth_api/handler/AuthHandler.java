package com.reactor.ms_auth_api.handler;

import com.reactor.ms_auth_api.JWTUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.reactor.ms_auth_api.domain.constant.MessageConstants.ERROR;

@Component
public class AuthHandler {

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(Map.class)
                .flatMap(body -> {
                    String username = (String) body.get("username");
                    if (username == null || username.isEmpty()) {
                        return ServerResponse.badRequest().bodyValue(Map.of(ERROR, "Username is required"));
                    }
                    String token = JWTUtil.generateToken(username);
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(Map.of("token", token));
                });
    }

    public Mono<ServerResponse> validateToken(ServerRequest request) {
        return request.queryParam("token")
                .map(token -> {
                    if (JWTUtil.validateToken(token)) {
                        String username = JWTUtil.getSubjectFromToken(token);
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(Map.of("username", username));
                    }
                    return ServerResponse.status(401).bodyValue(Map.of(ERROR, "Invalid token"));
                })
                .orElse(ServerResponse.badRequest().bodyValue(Map.of(ERROR, "Token is required")));
    }

    public Mono<ServerResponse> protectedResource(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("message", "Access granted to protected resource"));
    }
}
