package com.medcon.doctor.service.Impl;

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

        // Create availabilities if provided
        if (request.getAvailabilities() != null && !request.getAvailabilities().isEmpty()) {
            createDoctorAvailabilities(doctor);
        }

        doctor = doctorRepository.save(doctor);

        log.info("Doctor created successfully with ID: {}", doctor.getId());

        return doctorMapper.toDto(doctor);
    }

    @Override
    public DoctorResponse getDoctorByUserId(Long id) {
        return doctorMapper.toDto(doctorRepository.findByUserId(id).orElseThrow(() ->
                new NotFoundException("Doctor not found with ID: " + id, "Doctor")));
    }

    @Override
    public DoctorResponse updateDoctor(Long userId, DoctorRequest request) {
        log.info("Updating doctor with ID: {}", userId);

        Doctor doctor = doctorRepository.findByUserId(userId).orElseThrow(() ->
                new NotFoundException("Doctor not found with ID: " + userId, "Doctor"));

        if (request.getLicenseNumber() != null && !request.getLicenseNumber().equals(doctor.getLicenseNumber())) {
            if (doctorRepository.existsByLicenseNumber(request.getLicenseNumber()))
                throw new AlreadyExistsException("Doctor with license number " + request.getLicenseNumber() + " already exists.", "Doctor");

            doctor.setLicenseNumber(request.getLicenseNumber());
        }

        if (request.getSpecialization() != null) {
            doctor.setSpecialization(request.getSpecialization());
        }

        if (request.getExperience() != null) {
            doctor.setExperience(request.getExperience());
        }

        if (request.getBio() != null) {
            doctor.setBio(request.getBio());
        }

        if (request.getConsultationFee() != null) {
            doctor.setConsultationFee(request.getConsultationFee());
        }

        if (request.getAvailabilities() != null && !request.getAvailabilities().isEmpty()) {
            // Clear existing availabilities
            availabilityRepository.deleteAllByDoctorId(doctor.getId());
            createDoctorAvailabilities(doctor);
        }

        doctor = doctorRepository.save(doctor);

        log.info("Doctor updated successfully with ID: {}", userId);

        return doctorMapper.toDto(doctor);
    }

    // Private helper methods
    private void createDoctorAvailabilities(Doctor doctor) {
        for (var availability : doctor.getAvailabilities()) {
            validateAvailabilityRequest(availability);
            validateNoOverlappingAvailability(doctor.getId(), availability);
            availability.setDoctor(doctor);
        }
    }

    private void validateAvailabilityRequest(DoctorAvailability availability) {
        if (availability.getStartTime().isAfter(availability.getEndTime())) {
            throw new BusinessException("Start time cannot be after end time");
        }
    }

    private void validateNoOverlappingAvailability(Long doctorId, DoctorAvailability availability) {
        boolean hasOverlap = availabilityRepository.hasOverlappingAvailability(
                doctorId, availability.getDayOfWeek(), availability.getStartTime(),
                availability.getEndTime());

        if (hasOverlap) {
            throw new BusinessException("Overlapping availability found for the same day and time");
        }
    }
}
