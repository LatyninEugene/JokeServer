package ru.latynin.joke.collector.common;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.latynin.joke.collector.domain.common.RestExceptionResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestExceptionResponse> handleException(Exception e) {
        return new ResponseEntity<>(
                RestExceptionResponse.builder()
                        .message(e.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

}