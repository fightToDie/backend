package com.example.apigatewayservice;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final Environment env;

    public String getTokenSecret() {
        return env.getProperty("token.secret");
    }

    public boolean validateToken(String token) {
        boolean returnValue = true;

        String subject = null;

        try {
            subject = Jwts.parser().setSigningKey(getTokenSecret())
                              .parseClaimsJws(token).getBody()
                              .getSubject();
        } catch (Exception e) {
            returnValue = false;
        }

        if (subject == null || subject.isEmpty()) {
            returnValue = false;
        }

        return returnValue;
    }


}
