package com.bookmydoct.auth.data.entity;

import com.bookmydoct.auth.data.enums.AccountStatus;
import com.bookmydoct.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity{
    /**
     * Example:
     * John
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * Example:
     * Doe
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * Example:
     * john@gmail.com
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Example:
     * 9876543210
     */
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    /**
     * BCrypt encrypted password.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Profile image stored as blob.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] profileImage;

    /**
     * Account lifecycle status.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Column(nullable = false)
    @Builder.Default
    private Boolean emailVerified = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean mobileVerified = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}


