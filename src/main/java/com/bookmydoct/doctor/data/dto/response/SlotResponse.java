package com.bookmydoct.doctor.data.dto.response;
import com.bookmydoct.doctor.data.enums.SlotStatus;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlotResponse {

    private UUID uuid;
    private UUID doctorUuid;
    private String doctorName;
    private UUID scheduleUuid;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private SlotStatus status;
}
