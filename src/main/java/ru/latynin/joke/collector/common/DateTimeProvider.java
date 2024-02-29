package ru.latynin.joke.collector.common;

import java.time.Instant;

public class DateTimeProvider {

    public Instant getCurrentTime() {
        return Instant.now();
    }

}
