package ru.latynin.joke.collector.service;

import ru.latynin.joke.collector.domain.Joke;


public interface Joker<T extends Joke> {

    String getProviderId();

    //Возвращает шутки исключительно на английском языке
    T getJoke();

}
