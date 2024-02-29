package ru.latynin.joke.collector.helpers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.latynin.joke.collector.domain.dto.auth.AuthenticationResponseDto;
import ru.latynin.joke.collector.domain.dto.auth.RegisterRequestDto;
import ru.latynin.joke.collector.domain.entity.User;
import ru.latynin.joke.collector.repository.UserRepository;
import ru.latynin.joke.collector.service.security.AuthenticationService;

@Service
@RequiredArgsConstructor
public class UserHelper {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public User createUser(String email, String password) {
        authenticationService.register(RegisterRequestDto.builder()
                .email(email)
                .password(password)
                .build());
        return userRepository.findByEmail(email).orElseThrow();
    }

    public AuthenticationResponseDto createUserAndGetTokens(String email, String password) {
        return authenticationService.register(RegisterRequestDto.builder()
                .email(email)
                .password(password)
                .build());
    }

}
