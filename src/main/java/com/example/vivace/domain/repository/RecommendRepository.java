package com.example.vivace.domain.repository;

import com.example.vivace.domain.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    @Query("SELECT AVG(r.rating) FROM Recommend r")
    Double getRecommendAverageRating();

}
