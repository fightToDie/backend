package com.example.userservice.service;

import com.example.userservice.user.UserDto;

public interface UserService {

    UserDto getUserBySpotifyId(String spotifyId);

}
