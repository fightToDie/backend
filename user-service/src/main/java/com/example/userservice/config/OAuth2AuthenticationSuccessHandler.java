package com.example.userservice.config;

import com.example.userservice.service.UserService;
import com.example.userservice.user.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final Environment env;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String spotifyId = ((DefaultOAuth2User) authentication.getPrincipal()).getAttribute("id");
        UserDto userDetails = userService.getUserBySpotifyId(spotifyId);

        String token = Jwts.builder()
                               .setSubject(userDetails.getUserId())
                               .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(
                                       env.getProperty("token.expiration_time"))))
                               .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                               .compact();
        System.out.println(token);
        response.addHeader("token", token);
        response.addHeader("userId", userDetails.getUserId());
    }
}