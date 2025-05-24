package com.medcon.doctor.repository;

import com.medcon.doctor.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Find by user ID
    Optional<Doctor> findByUserId(Long userId);

    // Find by license number
    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    // Find by specialization
    Page<Doctor> findBySpecializationContainingIgnoreCase(String specialization, Pageable pageable);

    // Find verified doctors
    Page<Doctor> findByIsVerifiedTrue(Pageable pageable);

    // Find by specialization and verified status
    Page<Doctor> findBySpecializationContainingIgnoreCaseAndIsVerified(
            String specialization, boolean isVerified, Pageable pageable);

    // Find by average rating
    Page<Doctor> findByAverageRatingGreaterThanEqual(BigDecimal rating, Pageable pageable);

    // Find by experience range
    Page<Doctor> findByExperienceBetween(Integer minExperience, Integer maxExperience, Pageable pageable);

    // Find by consultation fee range
    Page<Doctor> findByConsultationFeeBetween(BigDecimal minFee, BigDecimal maxFee, Pageable pageable);

    // Complex search query
    @Query("SELECT d FROM Doctor d WHERE " +
            "(:specialization IS NULL OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :specialization, '%'))) AND " +
            "(:isVerified IS NULL OR d.isVerified = :isVerified) AND " +
            "(:minRating IS NULL OR d.averageRating >= :minRating) AND " +
            "(:minExperience IS NULL OR d.experience >= :minExperience) AND " +
            "(:maxExperience IS NULL OR d.experience <= :maxExperience) AND " +
            "(:minFee IS NULL OR d.consultationFee >= :minFee) AND " +
            "(:maxFee IS NULL OR d.consultationFee <= :maxFee)")
    Page<Doctor> findDoctorsWithFilters(
            @Param("specialization") String specialization,
            @Param("isVerified") Boolean isVerified,
            @Param("minRating") BigDecimal minRating,
            @Param("minExperience") Integer minExperience,
            @Param("maxExperience") Integer maxExperience,
            @Param("minFee") BigDecimal minFee,
            @Param("maxFee") BigDecimal maxFee,
            Pageable pageable
    );

    // Get top rated doctors
    @Query("SELECT d FROM Doctor d WHERE d.isVerified = true ORDER BY d.averageRating DESC")
    Page<Doctor> findTopRatedDoctors(Pageable pageable);

    // Get most experienced doctors
    @Query("SELECT d FROM Doctor d WHERE d.isVerified = true ORDER BY d.experience DESC")
    Page<Doctor> findMostExperiencedDoctors(Pageable pageable);

    // Count by specialization
    Long countBySpecializationContainingIgnoreCase(String specialization);
}
