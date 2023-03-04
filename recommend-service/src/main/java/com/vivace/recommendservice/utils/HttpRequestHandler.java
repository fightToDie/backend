package com.vivace.recommendservice.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class HttpRequestHandler {

    public ResponseEntity<String> getSearchTrackByTitleURI(String spotifyToken, String uri) {
        ResponseEntity<String> response = WebClient.create()
                .get()
                .uri(uri)
                .headers(headers -> {
                    headers.add(HttpHeaders.ACCEPT, "application/json");
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + spotifyToken);
                })
                .retrieve()
                .toEntity(String.class)
                .block();

        return response;
    }
}
