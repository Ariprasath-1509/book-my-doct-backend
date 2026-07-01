package com.bookmydoct.patient.repository;


import com.bookmydoct.patient.data.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {

    Optional<PatientProfile> findByUuid(UUID uuid);

    boolean existsByUserId(Long userId);

}
