package com.bookmydoct.doctor.controller;

import com.bookmydoct.common.response.ApiResponse;
import com.bookmydoct.doctor.data.dto.request.ScheduleRequest;
import com.bookmydoct.doctor.data.dto.request.UpdateScheduleRequest;
import com.bookmydoct.doctor.data.dto.response.ScheduleResponse;
import com.bookmydoct.doctor.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleResponse>> createSchedule(
            @Valid @RequestBody ScheduleRequest request) {

        ScheduleResponse response = scheduleService.createSchedule(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ScheduleResponse>builder()
                        .success(true)
                        .message("Schedule created successfully")
                        .data(response)
                        .build());
    }

    @GetMapping
    public ApiResponse<List<ScheduleResponse>> getAllSchedules() {

        List<ScheduleResponse> response = scheduleService.getAllSchedules();

        return ApiResponse.<List<ScheduleResponse>>builder()
                .success(true)
                .message("Schedules fetched successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{scheduleUuid}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> updateSchedule(
            @PathVariable
            UUID scheduleUuid,
            @Valid
            @RequestBody
            UpdateScheduleRequest request) {

        ScheduleResponse response = scheduleService.updateSchedule(scheduleUuid, request);

        return ResponseEntity.ok(
                ApiResponse.<ScheduleResponse>builder()
                        .success(true)
                        .message("Schedule updated successfully")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{scheduleUuid}")
    public ResponseEntity<ApiResponse<String>> deleteSchedule(
            @PathVariable
            UUID scheduleUuid) {

        scheduleService.deleteSchedule(scheduleUuid);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Schedule deleted successfully")
                        .data("Schedule deleted successfully")
                        .build()
        );
    }
}
