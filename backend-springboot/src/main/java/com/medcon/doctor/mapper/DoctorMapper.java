package com.medcon.doctor.mapper;

import com.medcon.doctor.dto.DoctorDto;
import com.medcon.doctor.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.email", target = "email"),
            @Mapping(source = "user.userProfile.firstName", target = "firstName"),
            @Mapping(source = "user.userProfile.lastName", target = "lastName"),
            @Mapping(source = "user.userProfile.address", target = "address"),
    })
    DoctorDto toDto(Doctor doctor);

    Doctor toEntity(DoctorDto doctorDTO);
}
