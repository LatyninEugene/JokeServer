package ru.latynin.joke.collector.service;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.latynin.joke.collector.common.DateTimeProvider;
import ru.latynin.joke.collector.common.mapper.JokeMapper;
import ru.latynin.joke.collector.domain.JokeWithId;
import ru.latynin.joke.collector.domain.common.Language;
import ru.latynin.joke.collector.domain.dto.JokeRequestDto;
import ru.latynin.joke.collector.domain.dto.JokeResponseDto;
import ru.latynin.joke.collector.domain.entity.JokeTranslation;
import ru.latynin.joke.collector.domain.entity.JokeHistory;
import ru.latynin.joke.collector.domain.entity.User;
import ru.latynin.joke.collector.repository.JokeHistoryRepository;
import ru.latynin.joke.collector.repository.JokeTranslationRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.VariableOperators.Let.ExpressionVariable.newVariable;

@Service
@RequiredArgsConstructor
public class JokeServiceImpl implements JokeService {

    private final Joker<JokeWithId> joker;
    private final JokeMapper jokeMapper;
    private final JokeTranslationRepository JokeTranslationRepository;
    private final JokeHistoryRepository jokeHistoryRepository;
    private final DateTimeProvider dateTimeProvider;
    private final MongoTemplate mongoTemplate;

    @Nullable
    @Autowired(required = false)
    private TextTranslator textTranslator;


    @Override
    public JokeResponseDto random(JokeRequestDto request) {
        var joke = getRandomJoke(request.getLanguage());
        return jokeMapper.toResponse(joke);
    }

    @Override
    public JokeResponseDto random(JokeRequestDto request, User user) {
        var joke = getRandomJoke(request.getLanguage());
        saveJokeTranslation(request, joke);
        saveJokeHistory(request, user, joke);
        return jokeMapper.toResponse(joke);
    }

    @Override
    public Slice<JokeResponseDto> getUserHistory(Pageable pageable, User user) {
        var filterCriteria = Criteria.where(JokeHistory.Fields.userId).is(user.getId());
        var stages = new ArrayList<AggregationOperation>();
        stages.add(match(filterCriteria));
        stages.add(Aggregation.sort(pageable.getSort()));
        stages.add(Aggregation.skip(pageable.getOffset()));
        stages.add(Aggregation.limit(pageable.getPageSize() + 1));
        stages.add(Aggregation.lookup().from(JokeTranslation.COLLECTION_NAME)
                .localField(JokeHistory.Fields.jokeId)
                .foreignField(JokeTranslation.Fields.JOKE_ID)
                .let(newVariable("history_lang").forField("$" + JokeHistory.Fields.selectedLanguage))
                .pipeline(match(ctx -> new Document("$expr", new Document("$eq", List.of("$$history_lang", "$" + JokeTranslation.Fields.LANG)))))
                .as("joke"));
        stages.add(Aggregation.unwind("joke"));
        stages.add(Aggregation.project()
                .andExpression("$joke.content.setup").as("setup")
                .andExpression("$joke.content.punchline").as("punchline"));
        Aggregation aggregation = Aggregation.newAggregation(stages);

        AggregationResults<JokeResponseDto> results = mongoTemplate.aggregate(
                aggregation,
                JokeHistory.class,
                JokeResponseDto.class);
        var result = results.getMappedResults();
        boolean hasNext = result.size() > pageable.getPageSize();
        return new SliceImpl<>(hasNext ? result.subList(0, pageable.getPageSize()) : result, pageable, hasNext);
    }

    private void saveJokeHistory(JokeRequestDto request, User user, JokeWithId joke) {
        var jokeHistory = JokeHistory.builder()
                .jokeId(String.valueOf(joke.getId()))
                .jokeProviderId(joker.getProviderId())
                .userId(user.getId())
                .selectedLanguage(request.getLanguage())
                .createdDate(dateTimeProvider.getCurrentTime())
                .build();
        jokeHistoryRepository.save(jokeHistory);
    }

    private void saveJokeTranslation(JokeRequestDto request, JokeWithId joke) {
        JokeTranslationRepository.save(JokeTranslation.builder()
                .id(JokeTranslation.JokeTranslationId.builder()
                        .jokeId(String.valueOf(joke.getId()))
                        .lang(request.getLanguage())
                        .build())
                .content(JokeTranslation.JokeContent.builder()
                        .setup(joke.getSetup())
                        .punchline(joke.getPunchline())
                        .build())
                .build());
    }

    private JokeWithId getRandomJoke(Language lang) {
        var joke = joker.getJoke();
        tryTranslate(lang, joke);
        return joke;
    }

    private void tryTranslate(Language lang, JokeWithId joke) {
        if (textTranslator != null && lang != Language.EN) {
            joke.setPunchline(textTranslator.translate(joke.getPunchline(), lang));
            joke.setSetup(textTranslator.translate(joke.getSetup(), lang));
        }
    }

}
