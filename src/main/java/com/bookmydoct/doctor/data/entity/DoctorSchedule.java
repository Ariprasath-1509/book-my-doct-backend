package com.bookmydoct.doctor.data.entity;

import com.bookmydoct.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="doctor_schedules")
public class DoctorSchedule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorProfile doctor;

    /**
     * Example:
     * MONDAY
     */
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    /**
     * Example:
     * 09:00
     */
    private LocalTime startTime;

    /**
     * Example:
     * 17:00
     */
    private LocalTime endTime;

    private Integer slotDurationInMinutes;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

}
