package com.example.userservice;

import java.net.UnknownHostException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final Environment env;

    @GetMapping("/health-check")
    public String status() throws UnknownHostException {
        return String.format("It's Working in User Service"
                                     + ", port(local.server.port)=" + env.getProperty(
                "local.server.port")
                                     + ", port(server.port)=" + env.getProperty("server.port")
                                     + ", client.id=" + env.getProperty(
                "spring.security.oauth2.client.registration.spotify.client-id")
                                     + ", redirect-uri=" + env.getProperty(
                "spring.security.oauth2.client.registration.spotify.redirect-uri"));
    }

    @GetMapping("/")
    public String index(Model model,
            @RegisteredOAuth2AuthorizedClient("spotify") OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OAuth2User oauth2User) {
        return String.format("userName=" + oauth2User.getName() + ", userAttribute="
                                     + oauth2User.getAttributes());
    }
}
