package com.vivace.recommendservice.repository;

import com.vivace.recommendservice.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
