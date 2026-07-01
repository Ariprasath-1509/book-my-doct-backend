package com.bookmydoct.appointment.scheduler;

import com.bookmydoct.appointment.data.entity.Appointment;
import com.bookmydoct.appointment.data.enums.AppointmentStatus;
import com.bookmydoct.appointment.repository.AppointmentRepository;
import com.bookmydoct.doctor.data.entity.Slot;
import com.bookmydoct.doctor.data.enums.SlotStatus;
import com.bookmydoct.doctor.repository.SlotRepository;
import com.bookmydoct.notification.service.EmailService;
import com.bookmydoct.payment.data.entity.Payment;
import com.bookmydoct.payment.data.enums.PaymentStatus;
import com.bookmydoct.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppointmentLockExpiryScheduler {

    private final AppointmentRepository appointmentRepository;
    private final SlotRepository slotRepository;
    private final EmailService emailService;
    private final PaymentRepository paymentRepository;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void releaseExpiredLocks() {

        List<Appointment> pendingAppointments =
                appointmentRepository.findByStatus(AppointmentStatus.PENDING);

        LocalDateTime now = LocalDateTime.now();

        for (Appointment appointment : pendingAppointments) {

            LocalDateTime lockExpiryTime =
                    appointment.getCreatedAt().plusMinutes(5);

            if (!lockExpiryTime.isAfter(now)) {
                appointment.setStatus(AppointmentStatus.CANCELLED);

                Slot slot = appointment.getSlot();
                slot.setStatus(SlotStatus.AVAILABLE);
                slotRepository.save(slot);

                List<Payment> payments = paymentRepository
                                .findByAppointment_UuidOrderByCreatedAtDesc(
                                        appointment.getUuid());

                for (Payment payment : payments) {
                    if (payment.getStatus() == PaymentStatus.PENDING) {
                        payment.setStatus(PaymentStatus.FAILED);
                        paymentRepository.save(payment);
                    }
                }

                appointmentRepository.save(appointment);

                log.info("Appointment {} cancelled due to payment timeout", appointment.getUuid());

                emailService.sendEmail(
                        appointment.getPatient()
                                .getUser()
                                .getEmail(),
                        "Appointment Cancelled",
                        """
                        Your appointment request
                        was automatically cancelled
                        because payment was not
                        completed within 5 minutes.
                        """
                );
            }
        }
    }

}