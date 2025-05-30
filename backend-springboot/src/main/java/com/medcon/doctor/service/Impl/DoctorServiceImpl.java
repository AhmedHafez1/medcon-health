package com.medcon.doctor.service.Impl;

import com.medcon.doctor.dto.DoctorAvailabilityDto;
import com.medcon.doctor.dto.DoctorResponse;
import com.medcon.doctor.dto.request.DoctorRequest;
import com.medcon.doctor.entity.Doctor;
import com.medcon.doctor.entity.DoctorAvailability;
import com.medcon.doctor.mapper.DoctorMapper;
import com.medcon.doctor.service.DoctorService;
import com.medcon.exception.AlreadyExistsException;
import com.medcon.exception.BusinessException;
import com.medcon.exception.NotFoundException;
import com.medcon.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.medcon.doctor.repository.*;
import com.medcon.user.repository.UserRepository;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;
    private final DoctorMapper doctorMapper;

    public DoctorResponse createDoctor(DoctorRequest request) {
        log.info("Creating doctor with request: {}", request);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + request.getUserId(), "User"));

        if (doctorRepository.existsByUserId(request.getUserId()))
            throw new AlreadyExistsException("User with ID " + request.getUserId() + " is already a doctor.", "Doctor");

        if (doctorRepository.existsByLicenseNumber(request.getLicenseNumber()))
            throw new AlreadyExistsException("Doctor with license number " + request.getLicenseNumber() + " already exists.", "Doctor");

        Doctor doctor = doctorMapper.toEntity(request);
        doctor.setUser(user);

        doctor = doctorRepository.save(doctor);

        // Create availabilities if provided
        if (request.getAvailabilities() != null && !request.getAvailabilities().isEmpty()) {
            createDoctorAvailabilities(doctor, request.getAvailabilities());
        }

        log.info("Doctor created successfully with ID: {}", doctor.getId());

        return doctorMapper.toDto(doctor);
    }

    // Private helper methods
    private void createDoctorAvailabilities(Doctor doctor, List<DoctorAvailabilityDto> availabilityDtos) {
        for (DoctorAvailabilityDto availabilityDto : availabilityDtos) {
            validateAvailabilityRequest(availabilityDto);
            validateNoOverlappingAvailability(doctor.getId(), availabilityDto);

            DoctorAvailability availability = doctorMapper.toAvailabilityEntity(availabilityDto);

            availabilityRepository.save(availability);
        }
    }

    private void validateAvailabilityRequest(DoctorAvailabilityDto availabilityDto) {
        if (availabilityDto.getStartTime().isAfter(availabilityDto.getEndTime())) {
            throw new BusinessException("Start time cannot be after end time");
        }
    }

    private void validateNoOverlappingAvailability(Long doctorId, DoctorAvailabilityDto availabilityDto) {
        boolean hasOverlap = availabilityRepository.hasOverlappingAvailability(
                doctorId, availabilityDto.getDayOfWeek(), availabilityDto.getStartTime(),
                availabilityDto.getEndTime());

        if (hasOverlap) {
            throw new BusinessException("Overlapping availability found for the same day and time");
        }
    }
}
