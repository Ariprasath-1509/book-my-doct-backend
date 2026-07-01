package com.bookmydoct.appointment.data.dto.response;

import com.bookmydoct.appointment.data.enums.AppointmentStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {

    private UUID appointmentUuid;

    private String appointmentReference;

    private UUID patientUuid;

    private String patientName;

    private UUID doctorUuid;

    private String doctorName;

    private UUID slotUuid;

    private LocalDate slotDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private AppointmentStatus status;
}