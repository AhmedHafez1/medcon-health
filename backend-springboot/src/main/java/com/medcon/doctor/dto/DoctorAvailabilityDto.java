package com.medcon.doctor.dto;

import java.time.LocalTime;

public record DoctorAvailabilityDto(Long doctorId, Integer dayOfWeek, LocalTime startTime, LocalTime endTime) {
}
