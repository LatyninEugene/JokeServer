package ru.latynin.joke.collector.common.client;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

public abstract class BaseClient {

    protected WebClient webClient;

    public <T> T get(String uri, Map<String, Object> params, Class<T> clazz) {
        var req = webClient.get().uri(builder -> {
            var uriBuilder = builder.path(uri);
            params.forEach(uriBuilder::queryParam);
            return uriBuilder.build();
        });
        return buildResponse(req, clazz);
    }

    public <T> T post(String uri, Object body, Class<T> clazz) {
        var req = webClient.post().uri(builder -> {
            var uriBuilder = builder.path(uri);
            return uriBuilder.build();
        });
        req.bodyValue(body);
        return buildResponse(req, clazz);
    }

    protected abstract <T> T buildResponse(WebClient.RequestHeadersSpec<?> request, Class<T> clazz);

}
