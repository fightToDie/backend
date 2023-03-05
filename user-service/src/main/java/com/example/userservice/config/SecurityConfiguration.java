package com.example.userservice.config;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final Environment env;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                                                              .requestMatchers("/", "/public/**",
                                                                      "/health-check").permitAll()
                                                              .anyRequest().authenticated()
                )
                .oauth2Login(
                        oauth2 -> oauth2.redirectionEndpoint(
                                redirection -> redirection.baseUri("/login/oauth2/code/*"))
                )
                .oauth2Client(withDefaults());
        return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.spotifyClientRegistration());
    }

    private ClientRegistration spotifyClientRegistration() {
        return ClientRegistration.withRegistrationId("spotify")
                       .clientId(env.getProperty(
                               "spring.security.oauth2.client.registration.spotify.client-id"))
                       .clientSecret(env.getProperty(
                               "spring.security.oauth2.client.registration.spotify.client-secret"))
                       .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                       .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                       .redirectUri(env.getProperty(
                               "spring.security.oauth2.client.registration.spotify.redirect-uri"))
                       .scope("user-read-private", "user-read-email")
                       .authorizationUri(env.getProperty(
                               "spring.security.oauth2.client.provider.spotify.authorization-uri"))
                       .tokenUri(env.getProperty(
                               "spring.security.oauth2.client.provider.spotify.token-uri"))
                       .userInfoUri(env.getProperty(
                               "spring.security.oauth2.client.provider.spotify.user-info-uri"))
                       .userNameAttributeName("id")
                       .jwkSetUri("http://localhost:8080")
                       .clientName("Nota")
                       .build();
    }


}
