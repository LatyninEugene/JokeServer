package ru.latynin.joke.collector.common.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Slice;
import ru.latynin.joke.collector.domain.dto.SliceResponseDto;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

    SliceResponseDto toResponse(Slice<?> slice);

}
