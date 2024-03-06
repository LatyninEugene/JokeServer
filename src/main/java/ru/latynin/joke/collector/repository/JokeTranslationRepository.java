package ru.latynin.joke.collector.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.latynin.joke.collector.domain.entity.JokeTranslation;

@Repository
public interface JokeTranslationRepository extends MongoRepository<JokeTranslation, JokeTranslation.JokeTranslationId> {
}
