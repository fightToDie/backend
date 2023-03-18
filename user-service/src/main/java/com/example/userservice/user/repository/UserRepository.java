package com.example.userservice.user.repository;

import com.example.userservice.user.dao.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySpotifyId(String spotifyId);
    List<User> findAll();
}
