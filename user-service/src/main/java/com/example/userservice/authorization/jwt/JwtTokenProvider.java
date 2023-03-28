package com.example.userservice.authorization.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
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

    public String createToken(String username) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 3600000); // 1 hour
        return Jwts.builder()
                       .setSubject(username)
                       .setIssuedAt(now)
                       .setExpiration(expirationDate)
                       .signWith(SignatureAlgorithm.HS512, getTokenSecret())
                       .compact();
    }

}
