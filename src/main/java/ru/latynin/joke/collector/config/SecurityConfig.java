package ru.latynin.joke.collector.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import ru.latynin.joke.collector.domain.common.RestExceptionResponse;
import ru.latynin.joke.collector.service.security.JwtTokenVerifierFilter;

import java.util.Arrays;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final JwtTokenVerifierFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;
    private final ObjectMapper objectMapper;

    public static String[] getPatternsToNotAuth() {
        return new String[]{
                "/api/v1/auth/**",
                "/public/**",
                "/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
        };
    }

    public static boolean matchNoAuthPath(@NonNull String path) {
        return Arrays.stream(getPatternsToNotAuth()).anyMatch(p -> antPathMatcher.match(p, path));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(getPatternsToNotAuth()).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint((request, response, authException) -> authenticationEntryPoint(response));
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @SneakyThrows
    private void authenticationEntryPoint(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        var errorResponse = RestExceptionResponse.builder()
                .message("Not Authorized")
                .build();
        String json = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(json);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

}
