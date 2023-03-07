package com.example.userservice;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/health-check"))
@RequiredArgsConstructor
public class HealthController {

    private final Environment env;

    @GetMapping
    public String status() {
        return String.format("It's Working in User Service"
                                     + ", port(local.server.port)=" + env.getProperty(
                "local.server.port")
                                     + ", port(server.port)=" + env.getProperty("server.port")
                                     + ", client.id=" + env.getProperty(
                "spring.security.oauth2.client.registration.spotify.client-id")
                                     + ", redirect-uri=" + env.getProperty(
                "spring.security.oauth2.client.registration.spotify.redirect-uri"));
    }

}
