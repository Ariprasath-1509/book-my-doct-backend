package com.bookmydoct.review.data.entity;

import com.bookmydoct.appointment.data.entity.Appointment;
import com.bookmydoct.common.entity.BaseEntity;
import com.bookmydoct.doctor.data.entity.DoctorProfile;
import com.bookmydoct.patient.data.entity.PatientProfile;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="reviews")
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorProfile doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    /**
     * Range:
     * 1-5
     */
    @Column(nullable = false)
    private Integer rating;

    /**
     * Patient feedback.
     */
    @Column(length = 1000)
    private String review;

}
