package com.example.userservice.service;

import com.example.userservice.repository.UserRepository;
import com.example.userservice.user.User;
import com.example.userservice.user.UserDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserBySpotifyId(String spotifyId) {
        Optional<User> user = userRepository.findBySpotifyId(spotifyId);
        if (user.isEmpty()) {
            // TODO: 예외처리
        }
        UserDto userDto = UserDto.builder()
                                  .userId(user.get().getId().toString())
                                  .build();
        return userDto;
    }
}
