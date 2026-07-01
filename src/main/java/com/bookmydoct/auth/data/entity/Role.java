package com.bookmydoct.auth.data.entity;

import com.bookmydoct.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role extends BaseEntity {
    /**
     * Unique role code.
     *
     * Examples:
     * PATIENT
     * DOCTOR
     * ADMIN
     */
    @Column(nullable = false, unique = true)
    private String roleCode;

    /**
     * Display name.
     *
     * Examples:
     * Patient
     * Doctor
     * Administrator
     */
    @Column(nullable = false)
    private String roleName;

    /**
     * Role explanation.
     */
    private String description;

}
