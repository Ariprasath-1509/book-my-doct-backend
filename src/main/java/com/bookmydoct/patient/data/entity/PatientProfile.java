package com.bookmydoct.patient.data.entity;

import com.bookmydoct.auth.data.entity.User;
import com.bookmydoct.common.entity.BaseEntity;
import com.bookmydoct.patient.data.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="patient_profiles")
public class PatientProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /**
     * Example:
     * 25
     */
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * Example:
     * O+
     * A-
     */
    private String bloodGroup;

    /**
     * Current residential address.
     */
    private String address;

}
