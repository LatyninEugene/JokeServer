package ru.latynin.joke.collector.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "application.external.yandex")
public class YandexProperties {

    private boolean enabled;
    private String oAuthToken;
    private String folderId;
    private String tokenUrl;
    private String translateApiUrl;

}
