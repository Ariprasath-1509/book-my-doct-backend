package com.bookmydoct.doctor.service.impl;

import com.bookmydoct.doctor.data.entity.DoctorSchedule;
import com.bookmydoct.doctor.data.entity.Slot;
import com.bookmydoct.doctor.data.enums.SlotStatus;
import com.bookmydoct.doctor.repository.ScheduleRepository;
import com.bookmydoct.doctor.repository.SlotRepository;
import com.bookmydoct.doctor.data.dto.request.GenerateSlotsRequest;
import com.bookmydoct.doctor.data.dto.response.SlotResponse;
import com.bookmydoct.common.exception.customException.ScheduleNotFoundException;
import com.bookmydoct.doctor.util.SlotMapper;
import com.bookmydoct.doctor.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {

    private final SlotRepository slotRepository;

    private final ScheduleRepository scheduleRepository;

    @Override
    public List<SlotResponse> generateSlots(
            UUID scheduleUuid,
            GenerateSlotsRequest request) {

        DoctorSchedule schedule = scheduleRepository.findByUuid(scheduleUuid)
                        .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));

        if (!request.getStartDate().isBefore(request.getEndDate())
                && !request.getStartDate().isEqual(request.getEndDate())) {

            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        List<Slot> generatedSlots = new ArrayList<>();

        LocalDate currentDate = request.getStartDate();

        while (!currentDate.isAfter(request.getEndDate())) {

            if (currentDate.getDayOfWeek() == schedule.getDayOfWeek()) {

                generatedSlots.addAll(generateSlotsForDate(schedule, currentDate));
            }

            currentDate = currentDate.plusDays(1);
        }

        List<Slot> savedSlots = slotRepository.saveAll(generatedSlots);

        return savedSlots.stream()
                .map(SlotMapper::toResponse)
                .toList();
    }

    private List<Slot> generateSlotsForDate(DoctorSchedule schedule, LocalDate date) {

        List<Slot> slots = new ArrayList<>();

        List<Slot> existingSlots = slotRepository.findByDoctorSchedule_UuidAndSlotDate(schedule.getUuid(), date);

        if (!existingSlots.isEmpty()) {
            return slots;
        }

        LocalTime cursor = schedule.getStartTime();

        int duration = schedule.getSlotDurationInMinutes();

        while (cursor.plusMinutes(duration).compareTo(schedule.getEndTime()) <= 0) {

            LocalTime slotEnd = cursor.plusMinutes(duration);

            Slot slot = Slot.builder()
                            .doctor(schedule.getDoctor())
                            .doctorSchedule(schedule)
                            .slotDate(date)
                            .startTime(cursor)
                            .endTime(slotEnd)
                            .status(SlotStatus.AVAILABLE)
                            .build();

            slots.add(slot);

            cursor = slotEnd;
        }

        return slots;
    }

    @Override
    public Page<SlotResponse> getDoctorSlots(UUID doctorUuid, Pageable pageable) {

        return slotRepository
                .findByDoctor_Uuid(doctorUuid, pageable)
                .map(SlotMapper::toResponse);
    }

    @Override
    public Page<SlotResponse> getAvailableSlots(
            UUID doctorUuid,
            Pageable pageable) {

        return slotRepository
                .findByDoctor_UuidAndStatus(
                        doctorUuid,
                        SlotStatus.AVAILABLE,
                        pageable)
                .map(SlotMapper::toResponse);
    }

    @Override
    public Page<SlotResponse> getAllSlots(
            Pageable pageable) {

        return slotRepository
                .findAll(pageable)
                .map(SlotMapper::toResponse);
    }
}