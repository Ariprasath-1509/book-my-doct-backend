package com.bookmydoct.appointment.util;

import com.bookmydoct.appointment.data.dto.response.AppointmentResponse;
import com.bookmydoct.appointment.data.entity.Appointment;

public final class AppointmentMapper {

    private AppointmentMapper() {}

    public static AppointmentResponse toResponse(Appointment appointment) {

        return AppointmentResponse.builder()
                .appointmentUuid(appointment.getUuid())
                .appointmentReference(appointment.getAppointmentReference())
                .patientUuid(appointment.getPatient().getUuid())
                .patientName(appointment.getPatient()
                                .getUser()
                                .getFirstName()
                                + " "
                                + appointment.getPatient()
                                .getUser()
                                .getLastName())
                .doctorUuid(appointment.getDoctor().getUuid())
                .doctorName(appointment.getDoctor()
                                .getUser()
                                .getFirstName()
                                + " "
                                + appointment.getDoctor()
                                .getUser()
                                .getLastName())
                .slotUuid(appointment.getSlot().getUuid())
                .slotDate(appointment.getSlot().getSlotDate())
                .startTime(appointment.getSlot().getStartTime())
                .endTime(appointment.getSlot().getEndTime())
                .status(appointment.getStatus())
                .build();
    }
}