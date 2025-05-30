package com.medcon.doctor.service;

import com.medcon.doctor.dto.DoctorResponse;
import com.medcon.doctor.dto.request.DoctorRequest;

public interface DoctorService {
    DoctorResponse createDoctor(DoctorRequest request);

    // Other service methods can be defined here as needed
}
