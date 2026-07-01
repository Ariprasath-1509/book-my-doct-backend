package com.bookmydoct.appointment.service.impl;

import com.bookmydoct.appointment.data.dto.response.AppointmentResponse;
import com.bookmydoct.appointment.data.entity.Appointment;
import com.bookmydoct.appointment.data.enums.AppointmentStatus;
import com.bookmydoct.appointment.repository.AppointmentRepository;
import com.bookmydoct.appointment.service.AppointmentLifecycleService;
import com.bookmydoct.appointment.util.AppointmentMapper;
import com.bookmydoct.appointment.util.AppointmentStateMachine;
import com.bookmydoct.common.exception.customException.AppointmentNotFoundException;
import com.bookmydoct.common.exception.customException.InvalidAppointmentStateException;
import com.bookmydoct.doctor.data.enums.SlotStatus;
import com.bookmydoct.doctor.repository.SlotRepository;
import com.bookmydoct.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentLifecycleServiceImpl implements AppointmentLifecycleService {

    private final AppointmentRepository appointmentRepository;
    private final SlotRepository slotRepository;
    private final EmailService emailService;

    @Override
    public AppointmentResponse acceptAppointment(UUID appointmentUuid) {
        return changeStatus(appointmentUuid, AppointmentStatus.CONFIRMED);
    }

    @Override
    public AppointmentResponse rejectAppointment(UUID appointmentUuid) {
        return changeStatus(appointmentUuid, AppointmentStatus.REJECTED);
    }

    @Override
    public AppointmentResponse completeAppointment(UUID appointmentUuid) {
        return changeStatus(appointmentUuid, AppointmentStatus.COMPLETED);
    }

    @Override
    public AppointmentResponse rescheduleAppointment(UUID appointmentUuid) {
        return changeStatus(appointmentUuid, AppointmentStatus.RESCHEDULED);
    }

    @Override
    public AppointmentResponse cancelAppointment(UUID appointmentUuid) {
        Appointment appointment = appointmentRepository.findByUuid(appointmentUuid)
                                     .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        if (!AppointmentStateMachine.isValidTransition(appointment.getStatus(), AppointmentStatus.CANCELLED)) {
            throw new InvalidAppointmentStateException(
                    "Cannot move appointment from "
                            + appointment.getStatus()
                            + " to CANCELLED");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.getSlot().setStatus(SlotStatus.AVAILABLE);
        slotRepository.save(appointment.getSlot());

        Appointment saved = appointmentRepository.save(appointment);
        sendCancellationNotification(saved);
        return AppointmentMapper.toResponse(saved);
    }


//    generic method that defines the business logic of appointment lifecycle management
    private AppointmentResponse changeStatus(UUID appointmentUuid, AppointmentStatus targetStatus) {

        Appointment appointment = appointmentRepository.findByUuid(appointmentUuid)
                                          .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        AppointmentStatus currentStatus = appointment.getStatus();

        if (!AppointmentStateMachine.isValidTransition(currentStatus, targetStatus)) {
            throw new InvalidAppointmentStateException(
                    "Cannot move appointment from "
                            + currentStatus
                            + " to "
                            + targetStatus);
        }

        appointment.setStatus(targetStatus);

        if (targetStatus == AppointmentStatus.CONFIRMED) {
            appointment.getSlot().setStatus(SlotStatus.BOOKED);
            slotRepository.save(appointment.getSlot());
        }
        else if (targetStatus == AppointmentStatus.REJECTED) {
            appointment.getSlot().setStatus(SlotStatus.AVAILABLE);
            slotRepository.save(appointment.getSlot());
        }

        Appointment saved = appointmentRepository.save(appointment);
        sendStatusNotification(saved);

        return AppointmentMapper.toResponse(saved);
    }

//    private method for email integration with appointment lifecycle methods except cancelAppoinment()
    private void sendStatusNotification(Appointment appointment) {

        switch (appointment.getStatus()) {
            case CONFIRMED -> {
                emailService.sendEmail(
                        appointment.getPatient()
                                .getUser()
                                .getEmail(),
                        "Appointment Confirmed",
                        """
                        Dear Patient,
    
                        Your appointment has been confirmed.
    
                        Appointment Reference: %s
    
                        Doctor: Dr. %s %s
    
                        Regards,
                        BookMyDoct Team
                        """.formatted(
                                appointment.getAppointmentReference(),
                                appointment.getDoctor()
                                        .getUser()
                                        .getFirstName(),
                                appointment.getDoctor()
                                        .getUser()
                                        .getLastName()
                        )
                );
            }

            case REJECTED -> {
                emailService.sendEmail(
                        appointment.getPatient()
                                .getUser()
                                .getEmail(),
                        "Appointment Rejected",
                        """
                        Dear Patient,
    
                        Your appointment request has been rejected.
    
                        Appointment Reference: %s
    
                        Regards,
                        BookMyDoct Team
                        """.formatted(
                                appointment.getAppointmentReference()
                        )
                );
            }

            case COMPLETED -> {
                emailService.sendEmail(
                        appointment.getPatient()
                                .getUser()
                                .getEmail(),
                        "Consultation Completed",
                        """
                        Dear Patient,
    
                        Your consultation has been completed.
    
                        Appointment Reference: %s
    
                        You can now submit a review.
    
                        Regards,
                        BookMyDoct Team
                        """.formatted(
                                appointment.getAppointmentReference()
                        )
                );
            }

            case RESCHEDULED -> {
                emailService.sendEmail(
                        appointment.getPatient()
                                .getUser()
                                .getEmail(),
                        "Appointment Rescheduled",
                        """
                        Dear Patient,
    
                        Your appointment has been rescheduled.
    
                        Appointment Reference: %s
    
                        Please check the updated schedule.
    
                        Regards,
                        BookMyDoct Team
                        """.formatted(
                                appointment.getAppointmentReference()
                        )
                );
            }

            default -> {
            }
        }
    }

//    private method for email integration with cancelAppointmment()
    private void sendCancellationNotification(Appointment appointment) {

        String subject = "Appointment Cancelled";
        String body =
                """
                Appointment Reference: %s
    
                The appointment has been cancelled.
    
                Regards,
                BookMyDoct Team
                """.formatted(
                        appointment.getAppointmentReference()
                );

        emailService.sendEmail(appointment.getPatient()
                        .getUser()
                        .getEmail(), subject, body
        );

        emailService.sendEmail(
                appointment.getDoctor()
                        .getUser()
                        .getEmail(),
                subject,
                body
        );
    }
}
