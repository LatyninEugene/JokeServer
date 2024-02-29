package ru.latynin.joke.collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.latynin.joke.collector.common.DateTimeProvider;
import ru.latynin.joke.collector.config.properties.JwtProperties;
import ru.latynin.joke.collector.controller.AuthController;
import ru.latynin.joke.collector.controller.UserController;
import ru.latynin.joke.collector.domain.dto.auth.AuthenticationRequestDto;
import ru.latynin.joke.collector.domain.dto.auth.AuthenticationResponseDto;
import ru.latynin.joke.collector.domain.dto.auth.RefreshTokenRequestDto;
import ru.latynin.joke.collector.domain.dto.auth.RegisterRequestDto;
import ru.latynin.joke.collector.helpers.UserHelper;
import ru.latynin.joke.collector.repository.UserRepository;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthTests extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private DateTimeProvider dateTimeProvider;

    @SneakyThrows
    @Test
    public void registration() {
        var userEmail = "1@gmail.com";
        var userPassword = "123";
        var requestDto = RegisterRequestDto.builder()
                .email(userEmail)
                .password(userPassword)
                .build();
        var result = mockMvc.perform(post(AuthController.API_ENDPOINT + "/register")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        var response = toObject(result, AuthenticationResponseDto.class);
        assertUserTokens(response, userEmail);
    }

    @SneakyThrows
    @Test
    public void login() {
        var userEmail = "2@gmail.com";
        var userPassword = "123";
        userHelper.createUser(userEmail, userPassword);

        var result = login(userEmail, userPassword).andExpect(status().isOk());
        var response = toObject(result, AuthenticationResponseDto.class);
        assertUserTokens(response, userEmail);
    }

    @SneakyThrows
    @Test
    public void refreshToken() {
        var userEmail = "3@gmail.com";
        var userPassword = "123";
        var userTokens = userHelper.createUserAndGetTokens(userEmail, userPassword);
        when(dateTimeProvider.getCurrentTime())
                .thenReturn(Instant.now()
                        .plusSeconds(TimeUnit.MINUTES.toSeconds(jwtProperties.getAccessTokenExpirationMinutes()) + 1));
        mockMvc.perform(get(UserController.API_ENDPOINT + "/self")).andExpect(status().isUnauthorized());

        var requestDto = RefreshTokenRequestDto.builder()
                .refreshToken(userTokens.getRefreshToken())
                .build();
        var result = mockMvc.perform(post(AuthController.API_ENDPOINT + "/refresh-token")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        var response = toObject(result, AuthenticationResponseDto.class);
        assertUserTokens(response, userEmail);
    }

    private void assertUserTokens(AuthenticationResponseDto response, String userEmail) {
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());

        var user = userRepository.findByEmail(userEmail);
        assertTrue(user.isPresent());
        assertEquals(user.get().getLastToken().getToken(), response.getAccessToken());
    }

    @NotNull
    private ResultActions login(String userEmail, String userPassword) throws Exception {
        var requestDto = AuthenticationRequestDto.builder()
                .email(userEmail)
                .password(userPassword)
                .build();
        return mockMvc.perform(post(AuthController.API_ENDPOINT + "/login")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON));
    }

}
