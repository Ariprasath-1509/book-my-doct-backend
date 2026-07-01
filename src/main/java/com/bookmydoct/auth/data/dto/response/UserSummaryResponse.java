package com.bookmydoct.auth.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryResponse {

    private UUID uuid;

    private String firstName;

    private String lastName;

    private String email;

    private String role;

    private String status;
}
