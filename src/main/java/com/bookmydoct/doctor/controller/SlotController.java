package com.bookmydoct.doctor.controller;

import com.bookmydoct.common.response.ApiResponse;
import com.bookmydoct.doctor.data.dto.request.GenerateSlotsRequest;
import com.bookmydoct.doctor.data.dto.response.SlotResponse;
import com.bookmydoct.doctor.service.SlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class SlotController {

    private final SlotService slotService;

    @PostMapping("/schedules/{scheduleUuid}/generate-slots")
    public ResponseEntity<ApiResponse<List<SlotResponse>>> generateSlots(
            @PathVariable
            UUID scheduleUuid,
            @Valid
            @RequestBody
            GenerateSlotsRequest request) {

        List<SlotResponse> response =
                slotService.generateSlots(
                        scheduleUuid,
                        request);

        return ResponseEntity.ok(
                ApiResponse.<List<SlotResponse>>builder()
                        .success(true)
                        .message("Slots generated successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{doctorUuid}/slots")
    public ResponseEntity<ApiResponse<Page<SlotResponse>>> getDoctorSlots(
            @PathVariable
            UUID doctorUuid,
            Pageable pageable) {

        Page<SlotResponse> response =
                slotService.getDoctorSlots(
                        doctorUuid,
                        pageable);

        return ResponseEntity.ok(
                ApiResponse.<Page<SlotResponse>>builder()
                        .success(true)
                        .message("Doctor slots fetched successfully")
                        .data(response)
                        .build()
        );

    }

    @GetMapping("/{doctorUuid}/available-slots")
    public ResponseEntity<ApiResponse<Page<SlotResponse>>> getAvailableSlots(
            @PathVariable
            UUID doctorUuid,
            Pageable pageable) {

        Page<SlotResponse> response =
                slotService.getAvailableSlots(
                        doctorUuid,
                        pageable);

        return ResponseEntity.ok(
                ApiResponse.<Page<SlotResponse>>builder()
                        .success(true)
                        .message("Available slots fetched successfully")
                        .data(response)
                        .build()
        );

    }

    @GetMapping("/slots")
    public ResponseEntity<ApiResponse<Page<SlotResponse>>> getAllSlots(
            Pageable pageable) {

        Page<SlotResponse> response =
                slotService.getAllSlots(
                        pageable);

        return ResponseEntity.ok(
                ApiResponse.<Page<SlotResponse>>builder()
                        .success(true)
                        .message("All slots fetched successfully")
                        .data(response)
                        .build()
        );

    }
}