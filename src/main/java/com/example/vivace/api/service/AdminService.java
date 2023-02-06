package com.example.vivace.api.service;

import com.example.vivace.api.response.UserRes;

import java.sql.Time;
import java.util.List;

public interface AdminService {
    List<UserRes> allUsers();

    Long usage();

    Time getAvgTime();

    Double getAvgRating();
}
