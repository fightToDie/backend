package com.example.userservice.service;

import com.example.userservice.repository.UserRepository;
import com.example.userservice.user.Role;
import com.example.userservice.user.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        save(oAuth2User);

        return oAuth2User;
    }

    public void save(OAuth2User oAuth2User) {
        String spotifyId = oAuth2User.getAttribute("id");
        Optional<User> optionalUser = userRepository.findBySpotifyId(spotifyId);
        if (optionalUser.isEmpty()) {
            User user = User.builder()
                                .spotifyId(spotifyId)
                                .role(Role.USER).build();
            userRepository.save(user);
        }
    }
}
