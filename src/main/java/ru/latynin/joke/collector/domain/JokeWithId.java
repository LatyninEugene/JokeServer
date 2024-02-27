package ru.latynin.joke.collector.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class JokeWithId extends Joke {

    int id;

}

