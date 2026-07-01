package com.bookmydoct.doctor.repository;

import com.bookmydoct.doctor.data.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    Optional<Specialization> findByCode(String code);

    boolean existsByName(String name);
    boolean existsByCode(String code);

    Optional<Specialization> findByUuid(UUID uuid);
}
