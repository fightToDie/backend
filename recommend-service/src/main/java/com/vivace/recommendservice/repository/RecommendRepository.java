package com.vivace.recommendservice.repository;

import com.vivace.recommendservice.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

}
