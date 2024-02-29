package ru.latynin.joke.collector.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.latynin.joke.collector.domain.dto.auth.AuthenticationRequestDto;
import ru.latynin.joke.collector.domain.dto.auth.AuthenticationResponseDto;
import ru.latynin.joke.collector.domain.dto.auth.RefreshTokenRequestDto;
import ru.latynin.joke.collector.domain.dto.auth.RegisterRequestDto;
import ru.latynin.joke.collector.service.security.AuthenticationService;

import static ru.latynin.joke.collector.controller.AuthController.API_ENDPOINT;

@RestController
@RequestMapping(value = API_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {

    public static final String API_ENDPOINT = "/api/v1/auth";

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto request) {
        return ResponseEntity.ok(service.refreshToken(request));
    }

}
