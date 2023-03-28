package com.example.userservice.authorization.controller;

import com.example.userservice.authorization.dto.Client;
import com.example.userservice.authorization.dto.SpotifyTokenResponse;
import com.example.userservice.authorization.repositroy.SpotifyTokenRepository;
import com.example.userservice.authorization.dao.SpotifyToken;
import com.example.userservice.authorization.dto.SpotifyRefreshTokenResponse;
import com.example.userservice.authorization.jwt.JwtTokenProvider;
import com.example.userservice.authorization.util.StringToBase64Encoder;
import com.example.userservice.user.dto.UserDto;
import com.example.userservice.user.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@RestController
public class SpotifyAuthorizationController {

    private final Client client;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final SpotifyTokenRepository spotifyTokenRepository;

    private final Environment env;

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

        // exchange authorization code for access token and refresh token
        SpotifyTokenResponse tokenResponse = exchangeAuthorizationCodeForTokens(code);

        // get user profile
        String spotifyId = getUserProfile(tokenResponse.getAccessToken());

        // save tokens to database
        SpotifyToken spotifyToken =
                SpotifyToken.builder()
                        .accessToken(tokenResponse.getAccessToken())
                        .refreshToken(tokenResponse.getRefreshToken())
                        .expiryDate(Instant.now().plusSeconds(tokenResponse.getExpiresIn()))
                        .build();
        spotifyTokenRepository.save(spotifyToken);

        // save user
        UserDto userDto = userService.getUserBySpotifyId(spotifyId);
        if (userDto == null) {
            userDto = userService.save(spotifyId);
        }
        System.out.println("userDto = " + userDto.toString());

        // issue JWT token to authenticated user
        String jwtToken = jwtTokenProvider.createToken(tokenResponse.getAccessToken());
        response.addHeader("Authorization", "Bearer " + jwtToken);
        response.sendRedirect(env.getProperty("login-redirect-uri"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Void> refreshToken(
            @RequestParam("refresh_token") String refreshToken, HttpServletResponse response) {
        SpotifyRefreshTokenResponse refreshTokenResponse = exchangeRefreshTokenForAccessToken(
                refreshToken);

        SpotifyToken spotifyToken = spotifyTokenRepository.findByRefreshToken(refreshToken)
                                            .orElse(null);
        if (spotifyToken == null) {
            // handle error - no refresh token found in database
        }
        spotifyToken.setAccessToken(refreshTokenResponse.getAccessToken());
        spotifyToken.setExpiryDate(Instant.now().plusSeconds(refreshTokenResponse.getExpiresIn()));
        spotifyTokenRepository.save(spotifyToken);

        // issue JWT token to authenticated user
        String jwtToken = jwtTokenProvider.createToken(spotifyToken.getAccessToken());
        response.addHeader("Authorization", "Bearer " + jwtToken);
        return ResponseEntity.ok().build();
    }

    private SpotifyRefreshTokenResponse exchangeRefreshTokenForAccessToken(String refreshToken) {
        // send POST request to Spotify API to exchange refresh token for new access token and refresh token
        // return response as SpotifyTokenResponse object

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(
                StringToBase64Encoder.encode(client.getId() + ":" + client.getSecret()));
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", refreshToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<SpotifyRefreshTokenResponse> response = restTemplate.postForEntity(
                client.getTokenUrl(),
                request,
                SpotifyRefreshTokenResponse.class);
        return response.getBody();
    }

    private SpotifyTokenResponse exchangeAuthorizationCodeForTokens(String code) {
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
        ResponseEntity<SpotifyTokenResponse> response = restTemplate.postForEntity(
                client.getTokenUrl(),
                request,
                SpotifyTokenResponse.class);

        System.out.println(response.getBody());
        return response.getBody();
    }

    private String getUserProfile(String token) {
        WebClient webClient = WebClient.builder()
                                      .baseUrl(client.getUserInfoUrl())
                                      .defaultHeader(HttpHeaders.CONTENT_TYPE,
                                              MediaType.APPLICATION_JSON_VALUE)
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
