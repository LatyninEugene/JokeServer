package ru.latynin.joke.collector;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.latynin.joke.collector.common.DateTimeProvider;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public DateTimeProvider getDateTimeProvider() {
        return Mockito.mock(DateTimeProvider.class);
    }

}
