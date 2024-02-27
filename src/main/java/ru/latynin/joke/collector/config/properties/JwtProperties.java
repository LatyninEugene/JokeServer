package ru.latynin.joke.collector.config.properties;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "application.security.jwt")
public class JwtProperties {

    private SignatureAlgorithm algorithm;
    private String accessSecretKey;
    private Integer accessTokenExpirationMinutes;
    private Integer refreshTokenExpirationHours;

}
