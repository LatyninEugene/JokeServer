package ru.latynin.joke.collector.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.latynin.joke.collector.domain.common.Language;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@FieldNameConstants
@Document(collection = JokeHistory.COLLECTION_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JokeHistory {

    public static final String COLLECTION_NAME = "joke_history";

    @Id
    @Builder.Default
    String id = UUID.randomUUID().toString();
    String jokeId;
    String jokeProviderId;
    String userId;
    Language selectedLanguage;
    Instant createdDate;

}
