package ru.latynin.joke.collector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.latynin.joke.collector.common.client.YandexTranslateClient;
import ru.latynin.joke.collector.config.properties.YandexProperties;
import ru.latynin.joke.collector.domain.common.Language;
import ru.latynin.joke.collector.domain.dto.yandex.TranslateRequestDto;
import ru.latynin.joke.collector.domain.dto.yandex.TranslateResponseDto;

import static ru.latynin.joke.collector.common.FeatureFlag.DEEPL_ENABLED;
import static ru.latynin.joke.collector.common.FeatureFlag.YANDEX_ENABLED;

@Service("yandexTranslator")
@RequiredArgsConstructor
@ConditionalOnProperty(value = YANDEX_ENABLED)
public class YandexTranslator implements TextTranslator {

    private final YandexTranslateClient client;
    private final YandexProperties properties;

    @Override
    public String translate(String textToTranslate, Language targetLanguage) {
        var request = TranslateRequestDto.builder()
                .folderId(properties.getFolderId())
                .texts(new String[]{ textToTranslate })
                .targetLanguageCode(getTargetLang(targetLanguage))
                .build();
        return client.post("/v2/translate", request, TranslateResponseDto.class)
                .getTranslations()[0]
                .getText();
    }

    private String getTargetLang(Language lang) {
        return switch (lang) {
            case EN -> "en";
            case RU -> "ru";
        };
    }

}
