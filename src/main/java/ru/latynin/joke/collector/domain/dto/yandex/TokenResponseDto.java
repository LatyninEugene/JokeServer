package ru.latynin.joke.collector.domain.dto.yandex;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenResponseDto {

    String iamToken;
    String expiresAt;

}
