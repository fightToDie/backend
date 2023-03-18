package com.example.userservice.user.service;

import com.example.userservice.user.dto.UserDto;
import java.util.List;

public interface UserService {

    UserDto getUserBySpotifyId(String spotifyId);
    UserDto save(String spotifyId);

    List<UserDto> getUserList();

}
