package com.bookmydoct.common.config;

import com.bookmydoct.auth.data.entity.Role;
import com.bookmydoct.auth.data.entity.User;
import com.bookmydoct.auth.data.enums.AccountStatus;
import com.bookmydoct.auth.repository.RoleRepository;
import com.bookmydoct.auth.repository.UserRepository;
import com.bookmydoct.doctor.data.dto.request.GenerateSlotsRequest;
import com.bookmydoct.doctor.data.entity.DoctorProfile;
import com.bookmydoct.doctor.data.entity.DoctorSchedule;
import com.bookmydoct.doctor.data.entity.Specialization;
import com.bookmydoct.doctor.data.enums.VerificationStatus;
import com.bookmydoct.doctor.repository.DoctorProfileRepository;
import com.bookmydoct.doctor.repository.ScheduleRepository;
import com.bookmydoct.doctor.repository.SpecializationRepository;
import com.bookmydoct.doctor.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Separate bean so that @Transactional is applied via Spring proxy
 * (self-calls inside DataSeeder would bypass the proxy).
 */
@Component
@RequiredArgsConstructor
public class DoctorSeederHelper {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SpecializationRepository specializationRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final ScheduleRepository scheduleRepository;
    private final SlotService slotService;

    /**
     * Seeds a single doctor in its own transaction.
     * Returns true if a new record was created, false if already seeded.
     */
    @Transactional
    public boolean seedOne(DataSeeder.DoctorSeed seed) {
        Role doctorRole = roleRepository.findByRoleCode("DOC")
            .orElseThrow(() -> new RuntimeException("[DataSeeder] DOC role not found"));

        User user = userRepository.findByEmail(seed.email()).orElseGet(() ->
            userRepository.save(User.builder()
                .firstName(seed.firstName())
                .lastName(seed.lastName())
                .email(seed.email())
                .phoneNumber(seed.phone())
                .password("Doctor@123")
                .role(doctorRole)
                .status(AccountStatus.ACTIVE)
                .emailVerified(true)
                .mobileVerified(true)
                .build()));

        if (doctorProfileRepository.existsByUserUuid(user.getUuid())) {
            return false;
        }

        Specialization spec = specializationRepository.findByCode(seed.specCode())
            .orElseThrow(() -> new RuntimeException("[DataSeeder] Specialization not found: " + seed.specCode()));

        DoctorProfile profile = doctorProfileRepository.save(DoctorProfile.builder()
            .user(user)
            .specialization(spec)
            .experienceYears(seed.experience())
            .consultationFee(seed.fee())
            .licenseNumber(seed.license())
            .clinicName(seed.clinic())
            .address(seed.address())
            .city(seed.city())
            .state(seed.state())
            .pincode(seed.pincode())
            .consultationMode(seed.mode())
            .verificationStatus(VerificationStatus.APPROVED)
            .averageRating(0.0)
            .totalReviews(0)
            .build());

        DoctorSchedule schedule = scheduleRepository.save(DoctorSchedule.builder()
            .doctor(profile)
            .dayOfWeek(seed.day())
            .startTime(LocalTime.of(9, 0))
            .endTime(LocalTime.of(17, 0))
            .slotDurationInMinutes(30)
            .active(true)
            .build());

        slotService.generateSlots(schedule.getUuid(),
            GenerateSlotsRequest.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(90))
                .build());

        return true;
    }
}
