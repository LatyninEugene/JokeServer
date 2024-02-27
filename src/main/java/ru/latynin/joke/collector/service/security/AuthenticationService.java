package ru.latynin.joke.collector.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.latynin.joke.collector.domain.common.Role;
import ru.latynin.joke.collector.domain.dto.auth.AuthenticationRequestDto;
import ru.latynin.joke.collector.domain.dto.auth.RefreshTokenRequestDto;
import ru.latynin.joke.collector.domain.entity.User;
import ru.latynin.joke.collector.domain.dto.auth.AuthenticationResponseDto;
import ru.latynin.joke.collector.domain.dto.auth.RegisterRequestDto;
import ru.latynin.joke.collector.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto register(RegisterRequestDto request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        user.updateToken(jwtToken);
        userRepository.save(user);
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        user.updateToken(jwtToken);
        userRepository.save(user);
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @SneakyThrows
    public AuthenticationResponseDto refreshToken(RefreshTokenRequestDto request) {
        var userEmail = jwtService.extractUsername(request.getRefreshToken());
        if (userEmail == null) {
            throw new Exception("Token not valid");
        }
        var user = userRepository.findByEmail(userEmail).orElseThrow();
        if (!jwtService.isTokenValid(request.getRefreshToken(), user)) {
            throw new Exception("Token not valid");
        }
        var accessToken = jwtService.generateToken(user);
        user.updateToken(accessToken);
        var authResponse = AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(request.getRefreshToken())
                .build();
        userRepository.save(user);
        return authResponse;
    }

}
