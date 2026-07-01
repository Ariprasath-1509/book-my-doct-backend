package com.bookmydoct.doctor.util;


import com.bookmydoct.doctor.data.dto.response.SlotResponse;
import com.bookmydoct.doctor.data.entity.Slot;

public class SlotMapper {

    private SlotMapper() {}

    public static SlotResponse toResponse(Slot slot) {

        String doctorFullName =
                slot.getDoctor().getUser().getFirstName() + " "
                        + slot.getDoctor().getUser().getLastName();

        return SlotResponse.builder()
                .uuid(slot.getUuid())
                .doctorUuid(slot.getDoctor().getUuid())
                .doctorName(doctorFullName)
                .scheduleUuid(slot.getDoctorSchedule().getUuid())
                .slotDate(slot.getSlotDate())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .status(slot.getStatus())
                .build();
    }

}
