package com.example.userservice.user.controller;

import com.example.userservice.user.dto.UserDto;
import com.example.userservice.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(("/member"))
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> user() {
        return ResponseEntity.ok(userService.getUserList());
    }
}
