package com.medcon.doctor.dto;

import com.medcon.doctor.entity.DoctorAvailability;

import java.math.BigDecimal;
import java.util.List;

public record DoctorDto(Long id, Long userId, String fullName, String email, String phoneNumber, String specialization,
                        String licenseNumber, Integer experience, String bio, BigDecimal consultationFee,
                        boolean isVerified, BigDecimal averageRating, Integer totalRatings,
                        List<DoctorAvailability> availabilities, String address) {
}
