package ru.latynin.joke.collector.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    public String token;

    public boolean revoked;

    public Instant createdDate;

}
