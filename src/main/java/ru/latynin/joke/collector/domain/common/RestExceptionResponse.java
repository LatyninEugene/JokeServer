package ru.latynin.joke.collector.domain.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestExceptionResponse {

    String message;

}
