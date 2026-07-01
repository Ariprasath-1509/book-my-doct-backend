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
public class RoleResponse {

    private UUID uuid;
    private String roleName;
    private String roleCode;
    private String description;
}
