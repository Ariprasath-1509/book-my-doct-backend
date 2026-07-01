package com.bookmydoct.auth.service.impl;

import com.bookmydoct.auth.data.dto.request.RoleRequest;
import com.bookmydoct.auth.data.dto.response.RoleResponse;
import com.bookmydoct.auth.data.entity.Role;
import com.bookmydoct.auth.repository.RoleRepository;
import com.bookmydoct.auth.service.RoleService;
import com.bookmydoct.auth.util.RoleMapper;
import com.bookmydoct.common.exception.customException.RoleAlreadyExistsException;
import com.bookmydoct.common.exception.customException.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleResponse saveRole(RoleRequest request) {

        if (roleRepository.existsByRoleName(request.getRoleName())) {
            throw new RoleAlreadyExistsException("Role name already exists");
        }

        if (roleRepository.existsByRoleCode(request.getRoleCode())) {
            throw new RoleAlreadyExistsException("Role code already exists");
        }

        Role role = RoleMapper.toEntity(request);
        Role savedRole = roleRepository.save(role);

        return RoleMapper.toResponse(savedRole);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleByUuid(UUID uuid) {

        Role role = roleRepository.findByUuid(uuid)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        return RoleMapper.toResponse(role);
    }

}
