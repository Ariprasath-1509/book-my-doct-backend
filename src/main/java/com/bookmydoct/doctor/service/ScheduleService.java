package com.bookmydoct.doctor.service;

import com.bookmydoct.doctor.data.dto.request.ScheduleRequest;
import com.bookmydoct.doctor.data.dto.request.UpdateScheduleRequest;
import com.bookmydoct.doctor.data.dto.response.ScheduleResponse;

import java.util.List;
import java.util.UUID;

public interface ScheduleService {

    ScheduleResponse createSchedule(ScheduleRequest request);

    List<ScheduleResponse> getAllSchedules();

    ScheduleResponse updateSchedule(UUID scheduleUuid, UpdateScheduleRequest request);

    void deleteSchedule(UUID scheduleUuid);
}
