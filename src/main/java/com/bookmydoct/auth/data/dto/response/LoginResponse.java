package com.bookmydoct.auth.data.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private UUID uuid;

    private String email;

    private String role;

    private String message;
}
