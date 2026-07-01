package com.bookmydoct.auth.controller;

import com.bookmydoct.auth.data.dto.request.RoleRequest;
import com.bookmydoct.auth.data.dto.response.RoleResponse;
import com.bookmydoct.auth.service.RoleService;
import com.bookmydoct.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // 🔒 Admin-only — checked manually using a header
    // Frontend/Postman must send: X-User-Role: ADMIN
    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> saveRole(
            @Valid @RequestBody RoleRequest request) {

        RoleResponse response = roleService.saveRole(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<RoleResponse>builder()
                        .success(true)
                        .message("Role created successfully")
                        .data(response)
                        .build());
    }

    // 🌐 Open to all users — no role check needed
    @GetMapping("/{uuid}")
    public ApiResponse<RoleResponse> getRoleByUuid(@PathVariable UUID uuid) {

        RoleResponse response = roleService.getRoleByUuid(uuid);

        return ApiResponse.<RoleResponse>builder()
                .success(true)
                .message("Role fetched successfully")
                .data(response)
                .build();
    }
}
