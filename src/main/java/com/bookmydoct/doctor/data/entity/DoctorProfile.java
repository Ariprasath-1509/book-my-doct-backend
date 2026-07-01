package com.bookmydoct.doctor.data.entity;

import com.bookmydoct.auth.data.entity.User;
import com.bookmydoct.common.entity.BaseEntity;
import com.bookmydoct.doctor.data.enums.ConsultationMode;
import com.bookmydoct.doctor.data.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="doctor_profiles")
public class DoctorProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id", nullable = false)
    private Specialization specialization;

    /**
     * Example:
     * 12
     */
    private Integer experienceYears;

    /**
     * Example:
     * 500.00
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal consultationFee;

    /**
     * Example:
     * 4.8
     */
    private Double averageRating;

    /**
     * Example:
     * 125
     */
    private Integer totalReviews;

    /**
     * Example:
     * KMC123456
     */
    private String licenseNumber;

    /**
     * Example:
     * Apollo Hospital
     */
    private String clinicName;

    /**
     * Example:
     * Bannerghatta Road
     */
    private String address;

    /**
     * Example:
     * Bangalore
     */
    private String city;

    /**
     * Example:
     * Karnataka
     */
    private String state;

    /**
     * Example:
     * 560076
     */
    private String pincode;

    @Enumerated(EnumType.STRING)
    private ConsultationMode consultationMode;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;

}
