package com.medcon.doctor.dto;

import com.medcon.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {
    private Long id;
    private UserDto user;
    private String specialization;
    private String licenseNumber;
    private Integer experience;
    private String bio;
    private BigDecimal consultationFee;
    private boolean isVerified;
    private BigDecimal averageRating;
    private Integer totalRatings;
    private List<DoctorAvailabilityDto> availabilities;
}
