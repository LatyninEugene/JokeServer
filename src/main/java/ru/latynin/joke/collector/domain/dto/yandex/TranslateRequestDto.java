package ru.latynin.joke.collector.domain.dto.yandex;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TranslateRequestDto {

    String folderId;
    String[] texts;
    String targetLanguageCode;

}
