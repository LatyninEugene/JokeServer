package ru.latynin.joke.collector.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.latynin.joke.collector.domain.common.Language;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@Document(collection = JokeTranslation.COLLECTION_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JokeTranslation {

    public static final String COLLECTION_NAME = "joke_translations";

    @Id
    JokeTranslationId id;
    JokeContent content;

    public static class Fields {
        public static final String JOKE_ID = "_id.jokeId";
        public static final String LANG = "_id.lang";
    }

    @Data
    @Builder
    public static class JokeTranslationId {
        String jokeId;
        Language lang;
    }

    @Builder
    public static class JokeContent {
        String setup;
        String punchline;
    }

}
