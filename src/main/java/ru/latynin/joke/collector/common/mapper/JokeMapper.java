package ru.latynin.joke.collector.common.mapper;

import org.mapstruct.Mapper;
import ru.latynin.joke.collector.domain.Joke;
import ru.latynin.joke.collector.domain.JokeWithId;
import ru.latynin.joke.collector.domain.dto.ExternalJokeResponseDto;
import ru.latynin.joke.collector.domain.dto.JokeResponseDto;

@Mapper(componentModel = "spring")
public interface JokeMapper {

    JokeResponseDto toResponse(Joke joke);
    JokeWithId toJoke(ExternalJokeResponseDto externalJokeResponseDto);


}
