package com.bookmydoct.doctor.util;

import com.bookmydoct.doctor.data.dto.response.ScheduleResponse;
import com.bookmydoct.doctor.data.entity.DoctorSchedule;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    public static ScheduleResponse toResponse(DoctorSchedule schedule) {

        String doctorFullName =
                schedule.getDoctor().getUser().getFirstName() + " "
                        + schedule.getDoctor().getUser().getLastName();

        return ScheduleResponse.builder()
                .uuid(schedule.getUuid())
                .doctorUuid(schedule.getDoctor().getUuid())
                .doctorName(doctorFullName)
                .dayOfWeek(schedule.getDayOfWeek())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .slotDurationInMinutes(schedule.getSlotDurationInMinutes())
                .active(schedule.getActive())
                .build();
    }

}
