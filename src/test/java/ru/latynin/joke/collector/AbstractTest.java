package ru.latynin.joke.collector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles(value = {"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = AbstractTest.SpringPropertyInitializer.class, classes = {TestConfig.class})
@Testcontainers
@Slf4j
public abstract class AbstractTest {

	private final static MongoDBContainer MONGO_DB_CONTAINER =
			new MongoDBContainer(DockerImageName.parse("mongo:latest"));

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		MONGO_DB_CONTAINER.start();
	}

	public static class SpringPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
			log.info("Testcontainers MongoDB url: {}", MONGO_DB_CONTAINER.getReplicaSetUrl());
			TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
					applicationContext,
					"spring.data.mongodb.uri=" + MONGO_DB_CONTAINER.getReplicaSetUrl()
			);
		}

	}

	@SneakyThrows
	protected <T> T toObject(ResultActions actions, TypeReference<T> ref) {
		String response = performRequest(actions);
		return OBJECT_MAPPER.readValue(response, ref);
	}

	@SneakyThrows
	protected <T> T toObject(ResultActions actions, Class<T> clazz) {
		String response = performRequest(actions);
		return OBJECT_MAPPER.readValue(response, clazz);
	}

	@SneakyThrows
	private String performRequest(ResultActions actions) {
		return actions.andReturn()
				.getResponse()
				.getContentAsString();
	}

}
