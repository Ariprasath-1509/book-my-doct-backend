package com.bookmydoct.appointment.service.impl;

import com.bookmydoct.appointment.data.dto.request.CreateAppointmentRequest;
import com.bookmydoct.appointment.data.dto.response.AppointmentResponse;
import com.bookmydoct.appointment.data.entity.Appointment;
import com.bookmydoct.appointment.data.enums.AppointmentStatus;
import com.bookmydoct.appointment.repository.AppointmentRepository;
import com.bookmydoct.common.exception.customException.*;
import com.bookmydoct.appointment.util.AppointmentMapper;
import com.bookmydoct.appointment.service.AppointmentService;
import com.bookmydoct.doctor.data.entity.DoctorProfile;
import com.bookmydoct.doctor.data.entity.Slot;
import com.bookmydoct.doctor.data.enums.SlotStatus;
import com.bookmydoct.doctor.repository.DoctorProfileRepository;
import com.bookmydoct.doctor.repository.SlotRepository;
import com.bookmydoct.notification.service.EmailService;
import com.bookmydoct.patient.data.entity.PatientProfile;
import com.bookmydoct.patient.repository.PatientProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final PatientProfileRepository patientRepository;

    private final DoctorProfileRepository doctorRepository;

    private final SlotRepository slotRepository;

    private final EmailService emailService;

    @Override
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {
        log.info("Creating appointment for patient UUID: {}, slot UUID: {}", request.getPatientUuid(), request.getSlotUuid());

        PatientProfile patient = patientRepository
                .findByUuid(request.getPatientUuid())
                .orElseThrow(() ->
                        new PatientNotFoundException(
                                "Patient not found"));

        Slot slot = slotRepository
                .findByUuidForUpdate(request.getSlotUuid())
                .orElseThrow(() ->
                        new SlotNotAvailableException(
                                "Slot not found"));

        DoctorProfile doctor = slot.getDoctor();

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        boolean slotExpired =
                slot.getSlotDate().isBefore(today) ||
                        (slot.getSlotDate().isEqual(today) &&
                                slot.getEndTime().isBefore(now)
                        );

        if (slotExpired) {
            log.warn("Slot {} has expired, marking as EXPIRED", slot.getUuid());
            slot.setStatus(SlotStatus.EXPIRED);
            slotRepository.save(slot);
            throw new SlotNotAvailableException(
                    "Selected slot has already expired");
        }

        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            log.warn("Slot {} is not available, current status: {}", slot.getUuid(), slot.getStatus());
            throw new SlotNotAvailableException(
                    "Selected slot is not available");
        }

        slot.setStatus(SlotStatus.LOCKED);

        slotRepository.save(slot);

        Appointment appointment = new Appointment();
        appointment.setAppointmentReference(generateAppointmentReference());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setSlot(slot);

        appointment.setStatus(AppointmentStatus.PENDING);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        log.info("Appointment created successfully with reference: {}", savedAppointment.getAppointmentReference());

        emailService.sendEmail(
                patient.getUser().getEmail(),
                "Appointment Booked Successfully",
                """
                Dear Patient,
    
                Your appointment has been booked successfully.
    
                Appointment Reference: %s
    
                Doctor: Dr. %s %s
    
                Date: %s
    
                Time: %s - %s
    
                Regards,
                BookMyDoct Team
                """.formatted(
                        savedAppointment.getAppointmentReference(),
                        doctor.getUser().getFirstName(),
                        doctor.getUser().getLastName(),
                        slot.getSlotDate(),
                        slot.getStartTime(),
                        slot.getEndTime()
                )
        );

        emailService.sendEmail(
                doctor.getUser().getEmail(),
                "New Appointment Booked",
                """
                Dear Doctor,
    
                A new appointment has been booked.
    
                Appointment Reference: %s
    
                Patient: %s %s
    
                Date: %s
    
                Time: %s - %s
    
                Regards,
                BookMyDoct Team
                """.formatted(
                        savedAppointment.getAppointmentReference(),
                        patient.getUser().getFirstName(),
                        patient.getUser().getLastName(),
                        slot.getSlotDate(),
                        slot.getStartTime(),
                        slot.getEndTime()
                )
        );

        return AppointmentMapper.toResponse(
                savedAppointment);
    }

    @Override
    @Transactional
    public AppointmentResponse getAppointment(UUID appointmentUuid) {
        log.debug("Fetching appointment with UUID: {}", appointmentUuid);

        Appointment appointment = appointmentRepository.findByUuid(appointmentUuid)
                        .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        return AppointmentMapper.toResponse(appointment);
    }

    @Override
    @Transactional
    public Page<AppointmentResponse> getPatientAppointments(UUID patientUuid, Pageable pageable) {

        return appointmentRepository
                .findByPatient_Uuid(patientUuid, pageable)
                .map(AppointmentMapper::toResponse);
    }

    @Override
    @Transactional
    public Page<AppointmentResponse> getDoctorAppointments(UUID doctorUuid, Pageable pageable) {

        return appointmentRepository
                .findByDoctor_Uuid(doctorUuid, pageable)
                .map(AppointmentMapper::toResponse);
    }

    private String generateAppointmentReference() {

        long count = appointmentRepository.count() + 1;

        return String.format("APT-%d-%06d", LocalDate.now().getYear(), count);
    }
}