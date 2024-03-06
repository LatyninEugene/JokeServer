package ru.latynin.joke.collector.common;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DateTimeProvider {

    public Instant getCurrentTime() {
        return Instant.now();
    }

}
