package com.medcon.doctor.dto.request;

import com.medcon.doctor.dto.DoctorAvailabilityDto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorUpdateRequest {
    @Size(max = 100, message = "Specialization must not exceed 100 characters")
    private String specialization;

    @Size(max = 50, message = "License number must not exceed 50 characters")
    private String licenseNumber;

    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 50, message = "Experience cannot exceed 50 years")
    private Integer experience;

    @Size(max = 1000, message = "Bio must not exceed 1000 characters")
    private String bio;

    @DecimalMin(value = "0.0", message = "Consultation fee cannot be negative")
    @DecimalMax(value = "10000.0", message = "Consultation fee cannot exceed 10000")
    private BigDecimal consultationFee;

    private List<DoctorAvailabilityDto> availabilities;
}
