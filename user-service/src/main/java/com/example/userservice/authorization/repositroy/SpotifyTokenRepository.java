package com.example.userservice.authorization.repositroy;

import com.example.userservice.authorization.dao.SpotifyToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotifyTokenRepository extends JpaRepository<SpotifyToken, Long> {

    Optional<SpotifyToken> findById(Long id);

    Optional<SpotifyToken> findByAccessToken(String accessToken);

    Optional<SpotifyToken> findByRefreshToken(String refreshToken);

}
