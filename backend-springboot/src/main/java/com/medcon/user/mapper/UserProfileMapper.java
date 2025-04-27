package com.medcon.user.mapper;

import com.medcon.user.dto.UserProfileDto;
import com.medcon.user.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.email", target = "email"),
            @Mapping(source = "user.role", target = "role")
    })
    UserProfileDto toDto(UserProfile userProfile);

    UserProfile toEntity(UserProfileDto userProfileDto);

    @Mappings({
            @Mapping(source = "userProfile.id", target = "id"),
            @Mapping(source = "userProfile.user.id", target = "userId"),
            @Mapping(source = "userProfile.user.email", target = "email"),
            @Mapping(source = "userProfile.user.role", target = "role"),
            @Mapping(source = "userProfileDto.firstName", target = "firstName"),
            @Mapping(source = "userProfileDto.lastName", target = "lastName"),
            @Mapping(source = "userProfileDto.dob", target = "dob"),
            @Mapping(source = "userProfileDto.gender", target = "gender"),
            @Mapping(source = "userProfileDto.phone", target = "phone"),
            @Mapping(source = "userProfileDto.address", target = "address"),
            @Mapping(source = "userProfileDto.profilePicture", target = "profilePicture"),
    })
    UserProfileDto mergeProfile(UserProfile userProfile, UserProfileDto userProfileDto);
}
