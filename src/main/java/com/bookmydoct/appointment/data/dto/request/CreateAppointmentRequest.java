package com.bookmydoct.appointment.data.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAppointmentRequest {

    @NotNull
    private UUID patientUuid;

    @NotNull
    private UUID slotUuid;
}
