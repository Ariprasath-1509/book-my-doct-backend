package com.bookmydoct.appointment.data.entity;

import com.bookmydoct.appointment.data.enums.AppointmentStatus;
import com.bookmydoct.common.entity.BaseEntity;
import com.bookmydoct.doctor.data.entity.DoctorProfile;
import com.bookmydoct.doctor.data.entity.Slot;
import com.bookmydoct.patient.data.entity.PatientProfile;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="appointments")
public class Appointment extends BaseEntity {

    /**
     * Example:
     * APT-2026-000001
     */
    @Column(nullable = false, unique = true)
    private String appointmentReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorProfile doctor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false, unique = true)
    private Slot slot;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

}
