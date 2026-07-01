package com.bookmydoct.doctor.repository;

import com.bookmydoct.doctor.data.entity.DoctorSchedule;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    List<DoctorSchedule> findByDoctor_UuidAndDayOfWeek(UUID doctorUuid, DayOfWeek dayOfWeek);

    Optional<DoctorSchedule> findByUuid(@NotNull(message = "Schedule id is required") UUID scheduleUuid);

}
