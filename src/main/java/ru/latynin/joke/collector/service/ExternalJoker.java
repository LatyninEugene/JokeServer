package ru.latynin.joke.collector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.latynin.joke.collector.common.client.ExternalJokeApiClient;
import ru.latynin.joke.collector.common.mapper.JokeMapper;
import ru.latynin.joke.collector.domain.JokeWithId;
import ru.latynin.joke.collector.domain.dto.ExternalJokeResponseDto;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExternalJoker implements Joker<JokeWithId> {

    public static final String PROVIDER_ID = "external_joker";

    private final ExternalJokeApiClient client;
    private final JokeMapper jokeMapper;

    @Override
    public String getProviderId() {
        return PROVIDER_ID;
    }

    @Override
    public JokeWithId getJoke() {
        var externalJokeResponseDto = client.get("/random", Map.of(), ExternalJokeResponseDto.class);
        return jokeMapper.toJoke(externalJokeResponseDto);
    }

}
