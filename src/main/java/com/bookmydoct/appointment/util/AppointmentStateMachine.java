package com.bookmydoct.appointment.util;

import com.bookmydoct.appointment.data.enums.AppointmentStatus;

import java.util.Map;
import java.util.Set;

public final class AppointmentStateMachine {

    private AppointmentStateMachine() {
    }

    private static final Map<AppointmentStatus,
            Set<AppointmentStatus>> ALLOWED_TRANSITIONS =
            Map.of(
                    AppointmentStatus.PENDING,
                    Set.of(
                            AppointmentStatus.CONFIRMED,
                            AppointmentStatus.REJECTED,
                            AppointmentStatus.CANCELLED
                    ),

                    AppointmentStatus.CONFIRMED,
                    Set.of(
                            AppointmentStatus.COMPLETED,
                            AppointmentStatus.CANCELLED,
                            AppointmentStatus.RESCHEDULED
                    ),

                    AppointmentStatus.RESCHEDULED,
                    Set.of(
                            AppointmentStatus.CONFIRMED,
                            AppointmentStatus.CANCELLED
                    ),

                    AppointmentStatus.REJECTED,
                    Set.of(),

                    AppointmentStatus.CANCELLED,
                    Set.of(),

                    AppointmentStatus.COMPLETED,
                    Set.of()
            );

    public static boolean isValidTransition(
            AppointmentStatus currentStatus,
            AppointmentStatus targetStatus) {

        return ALLOWED_TRANSITIONS
                .getOrDefault(
                        currentStatus,
                        Set.of())
                .contains(targetStatus);
    }
}