package ru.latynin.joke.collector.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Joke {

    String setup;
    String punchline;

}
