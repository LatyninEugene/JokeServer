package ru.latynin.joke.collector.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "application.rest")
public class RestConfigProperties {

    private int connectionTimeout;
    private int readTimeout;
    private int maxInMemorySizeBytes;

}
