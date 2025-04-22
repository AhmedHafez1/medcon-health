package com.medcon.patient.mapper;

import com.medcon.patient.dto.PatientDto;
import com.medcon.patient.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    @Mappings({
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "firstName", source = "user.userProfile.firstName"),
            @Mapping(target = "lastName", source = "user.userProfile.lastName"),
            @Mapping(target = "address", source = "user.userProfile.address"),
    })
    PatientDto toDto(Patient patient);

    Patient toEntity(PatientDto patientDto);
}
