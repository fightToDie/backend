package com.example.userservice.contorller;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/member")
public class OAuth2WebClientController {
    private final WebClient webClient;
    public OAuth2WebClientController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/info")
    ResponseEntity<String> explicit() {
        // @formatter:off
        String body = this.webClient
                              .get()
                              .attributes(clientRegistrationId("spotify"))
                              .retrieve()
                              .bodyToMono(String.class)
                              .block();

        return ResponseEntity.ok().body(body);
    }
}
