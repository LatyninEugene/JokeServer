package ru.latynin.joke.collector.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.latynin.joke.collector.domain.dto.JokeRequestDto;
import ru.latynin.joke.collector.service.JokeService;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/joke", produces = MediaType.APPLICATION_JSON_VALUE)
public class JokeController {

    private final JokeService jokeService;

    @GetMapping("/random")
    public ResponseEntity<?> getRandom(JokeRequestDto requestDto, Principal user) {
        return ResponseEntity.ok(jokeService.random(requestDto));
    }

}


