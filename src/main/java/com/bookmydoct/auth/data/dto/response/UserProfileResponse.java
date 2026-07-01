package com.bookmydoct.auth.data.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponse {

    private UUID uuid;

    private String firstName;

    private String lastName;

    private boolean hasProfileImage;
    private String profileImageUrl;

    private String email;

    private String phoneNumber;

    private String role;

    private String status;

    private LocalDateTime createdAt;

}
