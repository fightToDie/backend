package com.example.vivace.domain.repository;

import com.example.vivace.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //@Query("select avg(*) from user.....")
    Optional<Double> getAvgRating();
}
