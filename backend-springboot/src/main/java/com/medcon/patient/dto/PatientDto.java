package com.medcon.patient.dto;

public record PatientDto(
        Long id,
        Long userId,
        String fullName,
        String email,
        String phoneNumber,
        String emergencyContact,
        String bloodType,
        String address
) {
}
