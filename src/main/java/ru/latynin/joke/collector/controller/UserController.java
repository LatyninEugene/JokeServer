package ru.latynin.joke.collector.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.latynin.joke.collector.common.mapper.UserMapper;
import ru.latynin.joke.collector.domain.dto.UserDto;
import ru.latynin.joke.collector.domain.entity.User;

import java.security.Principal;

import static ru.latynin.joke.collector.controller.UserController.API_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    public static final String API_ENDPOINT = "/api/v1/user";

    private final UserMapper userMapper;

    @PostMapping("/self")
    public ResponseEntity<UserDto> self(Principal user) {
        return ResponseEntity.ok(userMapper.toDto((User) user));
    }

}
