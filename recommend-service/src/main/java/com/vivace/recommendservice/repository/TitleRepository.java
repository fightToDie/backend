package com.vivace.recommendservice.repository;

import com.vivace.recommendservice.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long> {
}
