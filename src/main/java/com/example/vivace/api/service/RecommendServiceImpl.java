package com.example.vivace.api.service;

import com.example.vivace.domain.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecommendServiceImpl implements RecommendService{
    private final RecommendRepository recommendRepository;

}
