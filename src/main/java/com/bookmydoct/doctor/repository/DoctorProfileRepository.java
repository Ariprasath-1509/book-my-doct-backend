package com.bookmydoct.doctor.repository;

import com.bookmydoct.doctor.data.entity.DoctorProfile;
import com.bookmydoct.doctor.data.enums.VerificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {

    Optional<DoctorProfile> findByUuid(UUID uuid);

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByUserUuid(UUID userUuid);

    @Query("""
        SELECT dp FROM DoctorProfile dp
        JOIN dp.user u
        JOIN dp.specialization s
        WHERE (:name IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
                             OR LOWER(u.lastName)  LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:specialization IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :specialization, '%')))
          AND (:city IS NULL OR LOWER(dp.city) LIKE LOWER(CONCAT('%', :city, '%')))
          AND dp.verificationStatus = :verificationStatus
        """)
    Page<DoctorProfile> searchDoctors(
            @Param("name") String name,
            @Param("specialization") String specialization,
            @Param("city") String city,
            VerificationStatus verificationStatus,
            Pageable pageable);

    Page<DoctorProfile> findByVerificationStatus(VerificationStatus verificationStatus, Pageable pageable);

    long countByVerificationStatus(VerificationStatus verificationStatus);

}