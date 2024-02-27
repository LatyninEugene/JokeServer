package ru.latynin.joke.collector.service;

import ru.latynin.joke.collector.domain.common.Language;

public interface TextTranslator {

    String translate(String textToTranslate, Language targetLanguage);

}
