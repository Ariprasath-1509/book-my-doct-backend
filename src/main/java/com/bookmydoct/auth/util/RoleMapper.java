package com.bookmydoct.auth.util;

import com.bookmydoct.auth.data.dto.request.RoleRequest;
import com.bookmydoct.auth.data.dto.response.RoleResponse;
import com.bookmydoct.auth.data.entity.Role;

public class RoleMapper {

    private RoleMapper() {}

    public static Role toEntity(RoleRequest request) {

        Role role = new Role();
        role.setRoleName(request.getRoleName());
        role.setRoleCode(request.getRoleCode());
        role.setDescription(request.getDescription());

        return role;
    }

    public static RoleResponse toResponse(Role role) {

        return new RoleResponse(
                role.getUuid(),
                role.getRoleName(),
                role.getRoleCode(),
                role.getDescription()
        );
    }
}
