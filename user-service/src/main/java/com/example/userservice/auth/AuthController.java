package com.example.userservice.auth;

import com.example.userservice.user.dto.UserDto;
import com.example.userservice.user.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final Client client;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<Void> login(HttpServletResponse response) throws IOException {
        System.out.println("AuthController.login");

        String state = UUID.randomUUID().toString();
        String url = String.format(
                "%s?response_type=code&client_id=%s&scope=%s&redirect_uri=%s&state=%s",
                client.getAuthUrl(), client.getId(), client.getScope(), client.getRedirectUrl(),
                state);
        response.sendRedirect(url);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/callback")
    public ResponseEntity<Void> callback(@RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpServletResponse response) throws IOException {
        System.out.println("AuthController.callback");

        // validate state parameter
        String token = getToken(code);
        String spotifyId = getSpotifyId(token);

        // save user
        UserDto userDto = userService.save(spotifyId);
        System.out.println("userDto = " + userDto.toString());

        // issue JWT token to authenticated user
        String jwtToken = jwtTokenProvider.createToken(token);
        response.addHeader("Authorization", "Bearer " + jwtToken);
        response.sendRedirect("http://localhost:8000");
        return ResponseEntity.ok().build();
    }

    private String getToken(String code) {
        System.out.println("AuthController.getToken");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(
                StringToBase64Encoder.encode(client.getId() + ":" + client.getSecret()));
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", client.getRedirectUrl());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(client.getTokenUrl(),
                request,
                JsonNode.class);
        System.out.println("access_token: " + response.getBody().get("access_token").asText());
        return response.getBody().get("access_token").asText();
    }

    private String getSpotifyId(String token) {
        System.out.println("AuthController.getUserId");

        WebClient webClient = WebClient.builder()
                                      .baseUrl(client.getUserInfoUrl())
                                      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                      .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                      .build();
        JsonNode response = webClient.get()
                                      .retrieve()
                                      .bodyToMono(JsonNode.class)
                                      .block();

        System.out.println("response = " + response);
        System.out.println("id = " + response.get("id").asText());
        return response.get("id").asText();
    }

}
