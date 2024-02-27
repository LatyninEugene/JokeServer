package ru.latynin.joke.collector.service;

import com.deepl.api.Translator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.latynin.joke.collector.domain.common.Language;

import static ru.latynin.joke.collector.common.FeatureFlag.DEEPL_ENABLED;
import static ru.latynin.joke.collector.common.FeatureFlag.YANDEX_ENABLED;

@Service("deeplTranslator")
@RequiredArgsConstructor
@ConditionalOnProperty(value = DEEPL_ENABLED)
public class DeeplTranslator implements TextTranslator {

    private final Translator translator;

    @Override
    @SneakyThrows
    public String translate(String textToTranslate, Language targetLanguage) {
        return translator.translateText(textToTranslate, null, getTargetLang(targetLanguage)).getText();
    }

    private String getTargetLang(Language lang) {
        return switch (lang) {
            case EN -> "en";
            case RU -> "ru";
        };
    }
}
