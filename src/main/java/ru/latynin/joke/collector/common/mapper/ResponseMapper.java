package ru.latynin.joke.collector.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Slice;
import ru.latynin.joke.collector.domain.dto.SliceResponseDto;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

    @Mapping(target = "hasNext", expression = "java(slice.hasNext())")
    SliceResponseDto toResponse(Slice<?> slice);

}
