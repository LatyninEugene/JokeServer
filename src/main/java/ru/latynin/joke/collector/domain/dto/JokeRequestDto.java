package ru.latynin.joke.collector.domain.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.latynin.joke.collector.domain.common.Language;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JokeRequestDto {

    Language language = Language.EN;

}
