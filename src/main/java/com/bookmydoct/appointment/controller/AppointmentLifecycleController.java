package com.bookmydoct.appointment.controller;

import com.bookmydoct.appointment.data.dto.response.AppointmentResponse;
import com.bookmydoct.appointment.service.AppointmentLifecycleService;
import com.bookmydoct.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentLifecycleController {

    private final AppointmentLifecycleService appointmentLifecycleService;

    @PutMapping("/{appointmentUuid}/accept")
    public ResponseEntity<ApiResponse<AppointmentResponse>> acceptAppointment(
            @PathVariable
            UUID appointmentUuid) {

        AppointmentResponse response =
                appointmentLifecycleService.acceptAppointment(appointmentUuid);

        return ResponseEntity.ok(
                ApiResponse.<AppointmentResponse>builder()
                        .success(true)
                        .message("Appointment accepted successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{appointmentUuid}/reject")
    public ResponseEntity<ApiResponse<AppointmentResponse>> rejectAppointment(
            @PathVariable
            UUID appointmentUuid) {

        AppointmentResponse response =
                appointmentLifecycleService.rejectAppointment(appointmentUuid);

        return ResponseEntity.ok(
                ApiResponse.<AppointmentResponse>builder()
                        .success(true)
                        .message("Appointment rejected successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{appointmentUuid}/cancel")
    public ResponseEntity<ApiResponse<AppointmentResponse>> cancelAppointment(
            @PathVariable
            UUID appointmentUuid) {

        AppointmentResponse response =
                appointmentLifecycleService.cancelAppointment(appointmentUuid);

        return ResponseEntity.ok(
                ApiResponse.<AppointmentResponse>builder()
                        .success(true)
                        .message("Appointment cancelled successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{appointmentUuid}/complete")
    public ResponseEntity<ApiResponse<AppointmentResponse>> completeAppointment(
            @PathVariable
            UUID appointmentUuid) {

        AppointmentResponse response =
                appointmentLifecycleService.completeAppointment(appointmentUuid);

        return ResponseEntity.ok(
                ApiResponse.<AppointmentResponse>builder()
                        .success(true)
                        .message("Appointment completed successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{appointmentUuid}/reschedule")
    public ResponseEntity<ApiResponse<AppointmentResponse>> rescheduleAppointment(
            @PathVariable
            UUID appointmentUuid) {

        AppointmentResponse response =
                appointmentLifecycleService.rescheduleAppointment(appointmentUuid);

        return ResponseEntity.ok(
                ApiResponse.<AppointmentResponse>builder()
                        .success(true)
                        .message("Appointment rescheduled successfully")
                        .data(response)
                        .build()
        );
    }
}