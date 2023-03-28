package com.example.userservice.authorization.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Client {
    private final Environment env;
    public String getAuthUrl() {
        return env.getProperty(
                "spring.security.oauth2.client.provider.spotify.authorization-uri");
    }

    public String getId() {
        return this.env.getProperty(
                "spring.security.oauth2.client.registration.spotify.client-id");
    }

    public String getSecret() {
        return env.getProperty(
                "spring.security.oauth2.client.registration.spotify.client-secret");
    }

    public String getScope() {
        return "user-read-private user-read-email";
    }

    public String getRedirectUrl() {
        return env.getProperty(
                "spring.security.oauth2.client.registration.spotify.redirect-uri");
    }

    public String getTokenUrl() {
        return env.getProperty(
                "spring.security.oauth2.client.provider.spotify.token-uri");
    }

    public String getUserInfoUrl() {
        return env.getProperty(
                "spring.security.oauth2.client.provider.spotify.user-info-uri");
    }
}
