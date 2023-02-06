package com.example.vivace.domain.repository;

import com.example.vivace.domain.entity.UserVisitLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVisitLogRepository extends JpaRepository<UserVisitLog, Long> {
}
