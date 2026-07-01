package com.bookmydoct.doctor.service.impl;

import com.bookmydoct.common.exception.customException.*;
import com.bookmydoct.doctor.data.dto.request.ScheduleRequest;
import com.bookmydoct.doctor.data.dto.request.UpdateScheduleRequest;
import com.bookmydoct.doctor.data.dto.response.ScheduleResponse;
import com.bookmydoct.doctor.data.entity.DoctorProfile;
import com.bookmydoct.doctor.data.entity.DoctorSchedule;
import com.bookmydoct.doctor.data.entity.Slot;
import com.bookmydoct.doctor.repository.SlotRepository;
import com.bookmydoct.doctor.util.ScheduleMapper;
import com.bookmydoct.doctor.repository.DoctorProfileRepository;
import com.bookmydoct.doctor.repository.ScheduleRepository;
import com.bookmydoct.doctor.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository doctorScheduleRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final SlotRepository slotRepository;

    @Override
    public ScheduleResponse createSchedule(ScheduleRequest request) {

        // 1. validate startTime < endTime
        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new InvalidScheduleException("Start time must be before end time");
        }

        // 2. validate slot duration fits within the schedule window
        long totalMinutes = Duration.between(
                request.getStartTime(), request.getEndTime()).toMinutes();

        if (request.getSlotDurationInMinutes() > totalMinutes) {
            throw new InvalidScheduleException("Slot duration cannot be longer than the schedule window (" + totalMinutes + " minutes)");
        }

        // 3. fetch doctor
        DoctorProfile doctor = doctorProfileRepository.findByUuid(request.getDoctorUuid())
                .orElseThrow(() -> new UserNotFoundException("Doctor not found"));

        // 4. check for overlapping schedule on same day for same doctor
        List<DoctorSchedule> existingSchedules =
                doctorScheduleRepository.findByDoctor_UuidAndDayOfWeek(
                        request.getDoctorUuid(), request.getDayOfWeek());

        for (DoctorSchedule existing : existingSchedules) {
            boolean overlaps = request.getStartTime().isBefore(existing.getEndTime())
                    && existing.getStartTime().isBefore(request.getEndTime());

            if (overlaps) {
                throw new ScheduleConflictException(
                        "Doctor already has an overlapping schedule on " + request.getDayOfWeek());
            }
        }

        // 5. save
        DoctorSchedule schedule = DoctorSchedule.builder()
                .doctor(doctor)
                .dayOfWeek(request.getDayOfWeek())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .slotDurationInMinutes(request.getSlotDurationInMinutes())
                .active(true)
                .build();

        DoctorSchedule saved = doctorScheduleRepository.save(schedule);

        return ScheduleMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponse> getAllSchedules() {
        return doctorScheduleRepository.findAll()
                .stream()
                .map(ScheduleMapper::toResponse)
                .toList();
    }

    @Override
    public ScheduleResponse updateSchedule(UUID scheduleUuid, UpdateScheduleRequest request) {

        DoctorSchedule schedule =
                doctorScheduleRepository.findByUuid(scheduleUuid)
                        .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));

        schedule.setDayOfWeek(request.getDayOfWeek());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setSlotDurationInMinutes(request.getSlotDurationInMinutes());
        schedule.setActive(request.getActive());

        DoctorSchedule updated = doctorScheduleRepository.save(schedule);

        return ScheduleMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteSchedule(UUID scheduleUuid) {

        DoctorSchedule schedule =
                doctorScheduleRepository
                        .findByUuid(scheduleUuid)
                        .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));

        List<Slot> slots = slotRepository.findByDoctorSchedule_Uuid(scheduleUuid);

        if (!slots.isEmpty()) {
            throw new ScheduleDeletionNotAllowedException(
                    "Cannot delete schedule because slots have already been generated.");
        }

        doctorScheduleRepository.delete(schedule);
    }

}