package com.medcon.auth.mapper;

import com.medcon.auth.dto.RegisterRequest;
import com.medcon.user.dto.UserDto;
import com.medcon.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    User toUser(RegisterRequest request);

    UserDto toUserDto(User user);
}
