package ru.latynin.joke.collector.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "application.external.joke-api")
public class ExternalJokeApiProperties {

    private String baseUrl;

}
