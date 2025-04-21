package com.medcon.doctor.mapper;

import com.medcon.doctor.dto.DoctorDto;
import com.medcon.doctor.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.fullName", target = "fullName"),
            @Mapping(source = "user.email", target = "email"),
            @Mapping(source = "user.address", target = "address"),
    })
    DoctorDto toDto(Doctor doctor);

    Doctor toEntity(DoctorDto doctorDTO);
}
