package com.medcon.doctor.entity;

import com.medcon.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String specialization;

    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private Integer experience;

    private String bio;

    @Column(name = "consultation_fee", nullable = false)
    private BigDecimal consultationFee;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "average_rating")
    private BigDecimal averageRating;

    @Column(name = "total_ratings")
    private Integer totalRatings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    private List<DoctorAvailability> availabilities;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", user=" + user +
                ", specialization='" + specialization + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", experience=" + experience +
                ", bio='" + bio + '\'' +
                ", consultationFee=" + consultationFee +
                ", isVerified=" + isVerified +
                ", averageRating=" + averageRating +
                ", totalRatings=" + totalRatings +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
