package com.example.userservice.user.service;

import com.example.userservice.user.dao.Role;
import com.example.userservice.user.repository.UserRepository;
import com.example.userservice.user.dao.User;
import com.example.userservice.user.dto.UserDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.print.attribute.standard.Destination;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserBySpotifyId(String spotifyId) {
        Optional<User> user = userRepository.findBySpotifyId(spotifyId);
        if (user.isEmpty()) {
            return null;
        }
        UserDto userDto = UserDto.builder()
                                  .userId(user.get().getId().toString())
                                  .build();
        return userDto;
    }

    @Override
    public UserDto save(String spotifyId) {
        UserDto userDto = getUserBySpotifyId(spotifyId);
        if (userDto == null) {
            User user = User.builder()
                                .spotifyId(spotifyId)
                                .role(Role.USER)
                                .build();
            userRepository.save(user);
            ModelMapper modelMapper = new ModelMapper();
            userDto = modelMapper.map(user, UserDto.class);
        }

        return userDto;
    }

    @Override
    public List<UserDto> getUserList() {
        ModelMapper modelMapper = new ModelMapper();
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = userList.stream()
                                            .map(source -> modelMapper.map(source, UserDto.class))
                                            .toList();

        return userDtoList;
    }


}
