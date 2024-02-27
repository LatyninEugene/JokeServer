package ru.latynin.joke.collector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.latynin.joke.collector.common.client.ExternalJokeApiClient;
import ru.latynin.joke.collector.config.properties.ExternalJokeApiProperties;

@Configuration
public class ExternalJokeApiConfig {

    @Bean
    public ExternalJokeApiClient externalJokeApiClient(WebClient webClient, ExternalJokeApiProperties properties) {
        return new ExternalJokeApiClient(webClient, properties.getBaseUrl());
    }

}
