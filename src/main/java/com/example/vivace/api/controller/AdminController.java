package com.example.vivace.api.controller;

import com.example.vivace.api.response.UserRes;
import com.example.vivace.api.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> users(){
        List<UserRes> allUsers = adminService.allUsers();
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }
    @GetMapping("/recommendation-usage")
    public ResponseEntity<?> recommendationUsage(){
        Long usage = adminService.usage();
        return ResponseEntity.status(HttpStatus.OK).body(usage);
    }
    @GetMapping("/average-time")
    public ResponseEntity<?> averageTime(){
        Time avgTime = adminService.getAvgTime();
        return ResponseEntity.status(HttpStatus.OK).body(avgTime);
    }
    @GetMapping("/average-star")
    public ResponseEntity<?> averageStar(){
        Double avgRating = adminService.getAvgRating();
        return ResponseEntity.status(HttpStatus.OK).body(avgRating);
    }
}