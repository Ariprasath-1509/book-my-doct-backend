package com.bookmydoct.auth.service;

import com.bookmydoct.auth.data.dto.request.RoleRequest;
import com.bookmydoct.auth.data.dto.response.RoleResponse;

import java.util.UUID;


public interface RoleService {

    RoleResponse saveRole(RoleRequest request);

    RoleResponse getRoleByUuid(UUID uuid);
}
