package com.example.vivace.api.controller;

import com.example.vivace.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/member")
public class UserController {
    private final UserService userService;

}
