package ru.latynin.joke.collector.domain.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JokeResponseDto {

    String setup;
    String punchline;

}
