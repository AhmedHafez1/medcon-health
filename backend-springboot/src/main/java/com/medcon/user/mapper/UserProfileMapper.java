package com.medcon.user.mapper;

import com.medcon.user.dto.UserProfileDto;
import com.medcon.user.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.fullName", target = "fullName"),
            @Mapping(source = "user.email", target = "email"),
            @Mapping(source = "user.role", target = "role"),
            @Mapping(source = "user.address", target = "address"),
    })
    UserProfileDto toDto(UserProfile userProfile);

    UserProfile toEntity(UserProfileDto userProfileDto);
}
