package com.medcon.doctor.mapper;

import com.medcon.doctor.dto.DoctorAvailabilityDto;
import com.medcon.doctor.dto.DoctorResponse;
import com.medcon.doctor.dto.request.DoctorRequest;
import com.medcon.doctor.entity.Doctor;
import com.medcon.doctor.entity.DoctorAvailability;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorResponse toDto(Doctor doctor);

    Doctor toEntity(DoctorRequest doctorRequest);

    DoctorAvailability toAvailabilityEntity(DoctorAvailabilityDto availabilityDto);

    DoctorAvailabilityDto toAvailabilityDto(DoctorAvailability availability);
}
