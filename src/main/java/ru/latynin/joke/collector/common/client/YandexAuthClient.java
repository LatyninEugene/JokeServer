package ru.latynin.joke.collector.common.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.latynin.joke.collector.config.properties.YandexProperties;
import ru.latynin.joke.collector.domain.dto.yandex.TokenResponseDto;

import java.time.Instant;
import java.util.Map;

@Slf4j
public class YandexAuthClient extends BaseClient {

    private final YandexProperties properties;

    private String token;
    private Instant expiresAt;

    public YandexAuthClient(WebClient webClient, YandexProperties properties) {
        this.properties = properties;
        this.webClient = webClient.mutate()
                .baseUrl(properties.getTokenUrl())
                .build();
    }

    @SneakyThrows
    public String getIAMToken() {
        if (token != null && !expiresAt.isBefore(Instant.now())) {
            return token;
        }
        var response = post("",
                Map.of("yandexPassportOauthToken", properties.getOAuthToken()),
                TokenResponseDto.class);
        token = response.getIamToken();
        expiresAt = Instant.parse(response.getExpiresAt());
        return token;
    }

    @Override
    protected <T> T buildResponse(WebClient.RequestHeadersSpec<?> request, Class<T> clazz) {
        return request.retrieve()
                .onStatus(
                        status -> !status.is2xxSuccessful(),
                        response -> response.bodyToMono(String.class)
                                    .flatMap(error -> {
                                        log.error("Response code: [{}]. Response body: [{}]", response.statusCode(), error);
                                        return Mono.error(new Exception("Fail yandex auth api request"));
                                    })
                        )
                .bodyToMono(clazz)
                .block();
    }
}
