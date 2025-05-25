package com.medcon.doctor.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSearchRequest {
    private String specialization;
    private Boolean isVerified;
    private BigDecimal minRating;
    private Integer minExperience;
    private Integer maxExperience;
    private BigDecimal minFee;
    private BigDecimal maxFee;
    private String sortBy = "id"; // id, experience, rating, fee
    private String sortDirection = "asc"; // asc, desc
    private int page = 0;
    private int size = 10;
}
