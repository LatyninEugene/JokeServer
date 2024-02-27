package ru.latynin.joke.collector.config;

import com.deepl.api.Translator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.latynin.joke.collector.config.properties.DeeplProperties;

import static ru.latynin.joke.collector.common.FeatureFlag.DEEPL_ENABLED;

@Configuration
@ConditionalOnProperty(value = DEEPL_ENABLED)
public class DeeplConfig {

    @Bean
    public Translator getDeeplTranslator(DeeplProperties properties) {
        return new Translator(properties.getAuthKey());
    }

}
