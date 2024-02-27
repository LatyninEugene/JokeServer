package ru.latynin.joke.collector.common.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class ExternalJokeApiClient extends BaseClient {

    public ExternalJokeApiClient(WebClient webClient, String baseUrl) {
        this.webClient = webClient.mutate()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    protected <T> T buildResponse(WebClient.RequestHeadersSpec<?> request, Class<T> clazz) {
        return request.retrieve()
                .onStatus(
                        status -> !status.is2xxSuccessful(),
                        response -> response.bodyToMono(String.class)
                                    .flatMap(error -> {
                                        log.error("Response code: [{}]. Response body: [{}]", response.statusCode(), error);
                                        return Mono.error(new Exception("Fail external joke api request"));
                                    })
                        )
                .bodyToMono(clazz)
                .block();
    }
}
