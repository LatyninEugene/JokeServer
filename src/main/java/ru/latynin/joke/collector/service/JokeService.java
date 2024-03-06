package ru.latynin.joke.collector.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ru.latynin.joke.collector.domain.dto.JokeRequestDto;
import ru.latynin.joke.collector.domain.dto.JokeResponseDto;
import ru.latynin.joke.collector.domain.entity.User;

public interface JokeService {

    JokeResponseDto random(JokeRequestDto request);

    JokeResponseDto random(JokeRequestDto request, User user);

    Slice<JokeResponseDto> getUserHistory(Pageable pageable, User user);

}
