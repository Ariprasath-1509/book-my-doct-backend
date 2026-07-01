package com.bookmydoct.doctor.service;

import com.bookmydoct.doctor.data.dto.request.GenerateSlotsRequest;
import com.bookmydoct.doctor.data.dto.response.SlotResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SlotService {

    List<SlotResponse> generateSlots(
            UUID scheduleUuid,
            GenerateSlotsRequest request);

    Page<SlotResponse> getDoctorSlots(
            UUID doctorUuid,
            Pageable pageable);

    Page<SlotResponse> getAvailableSlots(
            UUID doctorUuid,
            Pageable pageable);

    Page<SlotResponse> getAllSlots(
            Pageable pageable);

}
