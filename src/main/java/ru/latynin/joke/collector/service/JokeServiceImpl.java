package ru.latynin.joke.collector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.latynin.joke.collector.common.client.ExternalJokeApiClient;
import ru.latynin.joke.collector.common.mapper.JokeMapper;
import ru.latynin.joke.collector.domain.JokeWithId;
import ru.latynin.joke.collector.domain.common.Language;
import ru.latynin.joke.collector.domain.dto.ExternalJokeResponseDto;
import ru.latynin.joke.collector.domain.dto.JokeRequestDto;
import ru.latynin.joke.collector.domain.dto.JokeResponseDto;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class JokeServiceImpl implements JokeService {

    private final Joker<JokeWithId> joker;
    private final JokeMapper jokeMapper;

    @Nullable
    @Autowired(required = false)
    private TextTranslator textTranslator;


    @Override
    public JokeResponseDto random(JokeRequestDto request) {
        var joke = joker.getJoke();

        if (textTranslator != null && request.getLanguage() != Language.EN) {
            joke.setPunchline(textTranslator.translate(joke.getPunchline(), request.getLanguage()));
            joke.setSetup(textTranslator.translate(joke.getSetup(), request.getLanguage()));
        }

        return jokeMapper.toResponse(joke);
    }

}
