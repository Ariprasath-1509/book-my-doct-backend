package com.bookmydoct.appointment.repository;

import com.bookmydoct.appointment.data.entity.Appointment;
import com.bookmydoct.appointment.data.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByUuid(UUID uuid);

    Page<Appointment> findByPatient_Uuid(UUID patientUuid, Pageable pageable);

    Page<Appointment> findByDoctor_Uuid(UUID doctorUuid, Pageable pageable);

    boolean existsBySlot_Uuid(UUID slotUuid);

    List<Appointment> findByStatus(AppointmentStatus status);

    long countByStatus(AppointmentStatus status);

    @Query("""
    SELECT COUNT(a)
    FROM Appointment a
    WHERE a.slot.slotDate = :today
    """)
    long countTodayAppointments(LocalDate today);

}