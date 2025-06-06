package com.medcon.user.mapper;

import com.medcon.user.dto.UserDto;
import com.medcon.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
