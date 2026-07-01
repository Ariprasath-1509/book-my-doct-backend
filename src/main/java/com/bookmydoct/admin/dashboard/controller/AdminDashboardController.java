package com.bookmydoct.admin.dashboard.controller;

import com.bookmydoct.admin.dashboard.dto.DashboardResponse;
import com.bookmydoct.admin.dashboard.service.AdminDashboardService;
import com.bookmydoct.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {

        DashboardResponse response = dashboardService.getDashboard();

        return ResponseEntity.ok(
                ApiResponse.<DashboardResponse>builder()
                        .success(true)
                        .message("Dashboard fetched successfully")
                        .data(response)
                        .build()
        );
    }

}