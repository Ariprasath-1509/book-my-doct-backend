package com.bookmydoct.doctor.repository;

import com.bookmydoct.doctor.data.entity.Slot;
import com.bookmydoct.doctor.data.enums.SlotStatus;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    List<Slot> findByDoctorSchedule_UuidAndSlotDate(UUID scheduleUuid, LocalDate slotDate);

    Page<Slot> findByDoctor_Uuid(UUID doctorUuid, Pageable pageable);

    Page<Slot> findByDoctor_UuidAndStatus(
            UUID doctorUuid,
            SlotStatus status,
            Pageable pageable);

    List<Slot> findByDoctorSchedule_Uuid(UUID scheduleUuid);

    Optional<Slot> findByUuid(@NotNull UUID slotUuid);

    @Query("""
       SELECT s
       FROM Slot s
       WHERE s.status = com.bookmydoct.doctor.data.enums.SlotStatus.AVAILABLE
       AND (
            s.slotDate < :today
            OR
            (
                s.slotDate = :today
                AND s.endTime < :now
            )
       )
       """)
    List<Slot> findSlotsToExpire(
            @Param("today") LocalDate today,
            @Param("now") LocalTime now);

    @Modifying
    @Query("""
       DELETE
       FROM Slot s
       WHERE s.status =
             com.bookmydoct.doctor.data.enums.SlotStatus.EXPIRED
       AND s.slotDate < :today
       """)
    int deleteExpiredSlots(
            @Param("today")
            LocalDate today);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
       SELECT s
       FROM Slot s
       WHERE s.uuid = :slotUuid
       """)
    Optional<Slot> findByUuidForUpdate(
            @Param("slotUuid")
            UUID slotUuid);

}
