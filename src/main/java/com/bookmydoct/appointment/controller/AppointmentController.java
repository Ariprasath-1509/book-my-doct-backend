package com.bookmydoct.appointment.controller;

import com.bookmydoct.appointment.data.dto.request.CreateAppointmentRequest;
import com.bookmydoct.appointment.data.dto.response.AppointmentResponse;
import com.bookmydoct.appointment.service.AppointmentService;
import com.bookmydoct.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<ApiResponse<AppointmentResponse>> createAppointment(
            @Valid
            @RequestBody
            CreateAppointmentRequest request) {

        AppointmentResponse response = appointmentService.createAppointment(request);

        return ResponseEntity.ok(
                ApiResponse.<AppointmentResponse>builder()
                        .success(true)
                        .message("Appointment created successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/appointments/{appointmentUuid}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getAppointment(
            @PathVariable
            UUID appointmentUuid) {

        AppointmentResponse response = appointmentService.getAppointment(appointmentUuid);

        return ResponseEntity.ok(
                ApiResponse.<AppointmentResponse>builder()
                        .success(true)
                        .message("Appointment fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/patients/{patientUuid}/appointments")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getPatientAppointments(
            @PathVariable
            UUID patientUuid,
            Pageable pageable) {

        Page<AppointmentResponse> response = appointmentService.getPatientAppointments(patientUuid, pageable);

        return ResponseEntity.ok(
                ApiResponse.<Page<AppointmentResponse>>builder()
                        .success(true)
                        .message("Patient appointments fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/doctors/{doctorUuid}/appointments")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getDoctorAppointments(
            @PathVariable
            UUID doctorUuid,
            Pageable pageable) {

        Page<AppointmentResponse> response = appointmentService.getDoctorAppointments(doctorUuid, pageable);

        return ResponseEntity.ok(
                ApiResponse.<Page<AppointmentResponse>>builder()
                        .success(true)
                        .message("Doctor appointments fetched successfully")
                        .data(response)
                        .build()
        );
    }
}