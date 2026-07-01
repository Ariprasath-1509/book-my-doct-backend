package com.bookmydoct.doctor.data.entity;

import com.bookmydoct.common.entity.BaseEntity;
import com.bookmydoct.doctor.data.enums.SlotStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="slots")
public class Slot extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorProfile doctor;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="schedule_id", nullable = false)
    private DoctorSchedule doctorSchedule;

    /**
     * Example:
     * 2026-06-16
     */
    private LocalDate slotDate;

    /**
     * Example:
     * 10:00
     */
    private LocalTime startTime;

    /**
     * Example:
     * 10:30
     */
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private SlotStatus status;

}


