package ru.latynin.joke.collector.common.mapper;

import org.mapstruct.Mapper;
import ru.latynin.joke.collector.domain.dto.UserDto;
import ru.latynin.joke.collector.domain.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

}
