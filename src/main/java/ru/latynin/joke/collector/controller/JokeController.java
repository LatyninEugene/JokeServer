package ru.latynin.joke.collector.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.latynin.joke.collector.common.mapper.ResponseMapper;
import ru.latynin.joke.collector.config.OpenApiConfig;
import ru.latynin.joke.collector.domain.dto.JokeRequestDto;
import ru.latynin.joke.collector.service.JokeService;

import static ru.latynin.joke.collector.common.SecurityUtils.requireCurrentUser;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/joke", produces = MediaType.APPLICATION_JSON_VALUE)
public class JokeController {

    private final JokeService jokeService;
    private final ResponseMapper responseMapper;

    @Operation(summary = "Возвращает случайную шутку авторизованному пользователю",
            description = "Шутка будет записана в историю ранее полученных пользователем шуток",
            security = {@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)})
    @GetMapping("/random")
    public ResponseEntity<?> getRandom(JokeRequestDto requestDto) {
        return ResponseEntity.ok(jokeService.random(requestDto, requireCurrentUser()));
    }

    @Operation(summary = "Возвращает историю полученных шуток",
            security = {@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)})
    @GetMapping("/history")
    public ResponseEntity<?> getHistory(
            @SortDefault.SortDefaults({@SortDefault(sort = "createdDate", direction = Sort.Direction.ASC),})
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(responseMapper.toResponse(jokeService.getUserHistory(pageable, requireCurrentUser())));
    }

}


