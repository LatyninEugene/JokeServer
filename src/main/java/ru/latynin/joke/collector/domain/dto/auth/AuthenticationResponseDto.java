package ru.latynin.joke.collector.domain.dto.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponseDto {

    String accessToken;
    String refreshToken;

}
