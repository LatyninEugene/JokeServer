package ru.latynin.joke.collector.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = Joke.COLLECTION_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Joke {

    public static final String COLLECTION_NAME = "jokes";

    @Id
    int id;
    List<Translation> translations;

    static class Translation {
        String lang;
        String setup;
        String punchline;
    }

}
