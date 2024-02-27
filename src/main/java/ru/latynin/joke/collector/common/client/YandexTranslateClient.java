package ru.latynin.joke.collector.common.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.latynin.joke.collector.config.properties.YandexProperties;

@Slf4j
public class YandexTranslateClient extends BaseClient {

    private final YandexAuthClient authClient;

    public YandexTranslateClient(WebClient webClient, YandexAuthClient authClient, YandexProperties properties) {
        this.authClient = authClient;
        this.webClient = webClient.mutate()
                .baseUrl(properties.getTranslateApiUrl())
                .build();
    }


    @Override
    protected <T> T buildResponse(WebClient.RequestHeadersSpec<?> request, Class<T> clazz) {
        request.headers(header -> {
            header.setBearerAuth(authClient.getIAMToken());
        });
        return request.retrieve()
                .onStatus(
                        status -> !status.is2xxSuccessful(),
                        response -> response.bodyToMono(String.class)
                                    .flatMap(error -> {
                                        log.error("Response code: [{}]. Response body: [{}]", response.statusCode(), error);
                                        return Mono.error(new Exception("Fail yandex translate api request"));
                                    })
                        )
                .bodyToMono(clazz)
                .block();
    }
}
