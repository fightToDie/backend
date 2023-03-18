package com.example.apigatewayservice.filter;

import com.example.apigatewayservice.JwtTokenProvider;
import com.example.apigatewayservice.filter.JwtAuthorizationFilter.Config;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthorizationFilter extends AbstractGatewayFilterFactory<Config> {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String header = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String token = header.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            exchange.getRequest().mutate().header("Authorization", "Bearer " + token);
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // configuration properties
    }
}