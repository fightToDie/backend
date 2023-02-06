package com.example.vivace.api.service;

import com.example.vivace.api.response.UserRes;
import com.example.vivace.domain.repository.RecommendRepository;
import com.example.vivace.domain.repository.UserRepository;
import com.example.vivace.domain.repository.UserVisitLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;
    private final RecommendRepository recommendRepository;
    private final UserVisitLogRepository userVisitLogRepository;

    @Override
    public List<UserRes> allUsers() {
        return userRepository.findAll().stream()
                .map(UserRes::new)
                .collect(Collectors.toList());
    }

    @Override
    public Long usage() {
        return null;
    }

    @Override
    public Time getAvgTime() {
        return null;
    }

    @Override
    public Double getAvgRating() {
        return recommendRepository.getRecommendAverageRating();
    }
}
