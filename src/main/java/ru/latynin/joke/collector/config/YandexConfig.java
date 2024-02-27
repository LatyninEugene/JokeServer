package ru.latynin.joke.collector.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.latynin.joke.collector.common.client.YandexAuthClient;
import ru.latynin.joke.collector.common.client.YandexTranslateClient;
import ru.latynin.joke.collector.config.properties.YandexProperties;

import static ru.latynin.joke.collector.common.FeatureFlag.YANDEX_ENABLED;

@Configuration
@ConditionalOnProperty(value = YANDEX_ENABLED)
public class YandexConfig {

    @Bean
    public YandexAuthClient getAuthClient(WebClient webClient, YandexProperties properties) {
        return new YandexAuthClient(webClient, properties);
    }
    @Bean
    public YandexTranslateClient getTranslateClient(WebClient webClient, YandexAuthClient authClient, YandexProperties properties) {
        return new YandexTranslateClient(webClient, authClient, properties);
    }
}
