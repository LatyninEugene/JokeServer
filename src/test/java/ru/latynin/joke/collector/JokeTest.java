package ru.latynin.joke.collector;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.latynin.joke.collector.common.SecurityUtils;
import ru.latynin.joke.collector.domain.JokeWithId;
import ru.latynin.joke.collector.domain.dto.JokeRequestDto;
import ru.latynin.joke.collector.domain.dto.JokeResponseDto;
import ru.latynin.joke.collector.domain.dto.SliceResponseDto;
import ru.latynin.joke.collector.helpers.UserHelper;
import ru.latynin.joke.collector.service.JokeService;
import ru.latynin.joke.collector.service.Joker;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class JokeTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Joker<JokeWithId> joker;
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private JokeService jokeService;

    private final static String TEST_USER_ID = "joke-user-id";

    @BeforeEach
    public void beforeEach() {
        userHelper.loginAs(TEST_USER_ID, "secret");
    }

    @Test
    public void getHistory() throws Exception {
        var testJoke = JokeWithId.builder()
                .id(1)
                .setup("setup")
                .punchline("punchline")
                .build();
        when(joker.getJoke()).thenReturn(testJoke);

        jokeService.random(new JokeRequestDto(), SecurityUtils.requireCurrentUser());

        var result = mockMvc.perform(get("/api/v1/joke/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        var response = toObject(result, new TypeReference<SliceResponseDto<JokeResponseDto>>() {});
        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertEquals(testJoke.getPunchline(), response.getContent().get(0).getPunchline());
    }

}
