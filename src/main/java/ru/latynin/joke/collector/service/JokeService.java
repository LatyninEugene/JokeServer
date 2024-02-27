package ru.latynin.joke.collector.service;

import ru.latynin.joke.collector.domain.dto.JokeRequestDto;
import ru.latynin.joke.collector.domain.dto.JokeResponseDto;

public interface JokeService {

    JokeResponseDto random(JokeRequestDto request);

}
