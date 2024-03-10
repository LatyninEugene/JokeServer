package ru.latynin.joke.collector.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.latynin.joke.collector.common.mapper.UserMapper;
import ru.latynin.joke.collector.config.OpenApiConfig;
import ru.latynin.joke.collector.domain.dto.UserDto;

import static ru.latynin.joke.collector.common.SecurityUtils.requireCurrentUser;
import static ru.latynin.joke.collector.controller.UserController.API_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    public static final String API_ENDPOINT = "/api/v1/user";

    private final UserMapper userMapper;

    @Operation(summary = "Возвращает информацию о текущем авторизованном пользователе",
            security = {@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)})
    @GetMapping("/self")
    public ResponseEntity<UserDto> self() {
        return ResponseEntity.ok(userMapper.toDto((requireCurrentUser())));
    }

}
