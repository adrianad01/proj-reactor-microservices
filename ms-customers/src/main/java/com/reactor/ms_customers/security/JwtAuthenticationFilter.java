package com.reactor.ms_customers.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    private static final String SECRET = "mySuperSecretKeyForJWTAuthentication123456";
    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Permitir acceso a rutas públicas sin autenticación
        List<String> publicEndpoints = List.of("/actuator/health", "/actuator/info");
        if (publicEndpoints.contains(request.getPath().toString())) {
            return chain.filter(exchange);
        }

        // Obtener el token del encabezado Authorization
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorizedResponse(exchange);
        }

        String token = authHeader.substring(7); // Remover "Bearer "
        if (!validateToken(token)) {
            return unauthorizedResponse(exchange);
        }

        return chain.filter(exchange);
    }

    private boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("✅ Token válido - Usuario: " + claims.getSubject());
            return claims.getSubject() != null;
        } catch (Exception e) {
            System.out.println("❌ Error validando token: " + e.getMessage());
            return false;
        }
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
