package com.medcon.doctor.repository;

import com.medcon.doctor.entity.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
    // Find by doctor ID
    List<DoctorAvailability> findByDoctorIdOrderByDayOfWeekAscStartTimeAsc(Long doctorId);

    // Find by doctor and day of week
    List<DoctorAvailability> findByDoctorIdAndDayOfWeek(Long doctorId, Integer dayOfWeek);

    // Check for overlapping availability
    @Query("SELECT COUNT(da) > 0 FROM DoctorAvailability da WHERE " +
            "da.doctor.id = :doctorId AND da.dayOfWeek = :dayOfWeek AND " +
            "((:endTime >= da.startTime AND :endTime <= da.endTime) OR " +
            "(:startTime >= da.startTime  AND :startTime <= da.endTime))")
    boolean hasOverlappingAvailability(
            @Param("doctorId") Long doctorId,
            @Param("dayOfWeek") Integer dayOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    // Delete by doctor ID
    void deleteByDoctorId(Long doctorId);
}
