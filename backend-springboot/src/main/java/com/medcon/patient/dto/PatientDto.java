package com.medcon.patient.dto;

public record PatientDto(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String emergencyContact,
        String bloodType,
        String address
) {
}
