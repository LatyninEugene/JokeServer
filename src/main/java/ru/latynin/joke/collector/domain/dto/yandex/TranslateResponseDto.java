package ru.latynin.joke.collector.domain.dto.yandex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranslateResponseDto {

    private Translation[] translations;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Translation {
        private String text;
    }

}
