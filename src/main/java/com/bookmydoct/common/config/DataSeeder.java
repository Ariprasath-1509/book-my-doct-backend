package com.bookmydoct.common.config;

import com.bookmydoct.auth.data.entity.Role;
import com.bookmydoct.auth.repository.RoleRepository;
import com.bookmydoct.doctor.data.entity.Specialization;
import com.bookmydoct.doctor.data.enums.ConsultationMode;
import com.bookmydoct.doctor.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final SpecializationRepository specializationRepository;
    private final DoctorSeederHelper doctorSeederHelper;

    public record DoctorSeed(
        String firstName, String lastName, String email, String phone,
        String license, String clinic, String address, String city,
        String state, String pincode, int experience, BigDecimal fee,
        ConsultationMode mode, DayOfWeek day, String specCode
    ) {}

    @Override
    public void run(ApplicationArguments args) {
        if (!roleRepository.existsByRoleCode("PATIENT")) {
            seedRoles();
        }
        if (!specializationRepository.existsByCode("CARDIOLOGY")) {
            seedSpecializations();
        }
        seedDoctors();
    }

    private void seedRoles() {
        roleRepository.saveAll(List.of(
            Role.builder().roleCode("PATIENT").roleName("Patient")
                .description("Can search and book appointments with doctors").build(),
            Role.builder().roleCode("DOC").roleName("Doctor")
                .description("Can manage schedule and handle patient appointments").build(),
            Role.builder().roleCode("ADMIN").roleName("Administrator")
                .description("Platform administrator with full access").build()
        ));
        log.info("[DataSeeder] Seeded 3 roles: PATIENT, DOC, ADMIN");
    }

    private void seedSpecializations() {
        specializationRepository.saveAll(List.of(
            spec("CARDIOLOGY",      "Cardiology",       "Heart and blood vessel conditions"),
            spec("DERMATOLOGY",     "Dermatology",      "Skin, hair and nail conditions"),
            spec("NEUROLOGY",       "Neurology",        "Brain and nervous system disorders"),
            spec("ORTHOPEDICS",     "Orthopedics",      "Musculoskeletal system conditions"),
            spec("PEDIATRICS",      "Pediatrics",       "Medical care for infants and children"),
            spec("PSYCHIATRY",      "Psychiatry",       "Mental health and behavioral disorders"),
            spec("OPHTHALMOLOGY",   "Ophthalmology",    "Eye and vision care"),
            spec("GYNECOLOGY",      "Gynecology",       "Women's reproductive health"),
            spec("ENT",             "ENT",              "Ear, nose and throat conditions"),
            spec("GENERAL_MEDICINE","General Medicine", "Primary care and general health")
        ));
        log.info("[DataSeeder] Seeded 10 specializations");
    }

    private Specialization spec(String code, String name, String desc) {
        return Specialization.builder().code(code).name(name).description(desc).active(true).build();
    }

    private void seedDoctors() {
        List<DoctorSeed> seeds = List.of(
            new DoctorSeed("Rajesh",  "Kumar",  "rajesh.kumar@bookmydoct.com",  "9000000001",
                "KMC100001", "Apollo Specialty Hospital",  "Anna Salai",     "Chennai",    "Tamil Nadu",  "600002", 15, new BigDecimal("800"), ConsultationMode.IN_PERSON, DayOfWeek.MONDAY,    "CARDIOLOGY"),
            new DoctorSeed("Priya",   "Sharma", "priya.sharma@bookmydoct.com",  "9000000002",
                "MCI100002", "Skin & Glow Clinic",         "Linking Road",   "Mumbai",     "Maharashtra", "400050", 10, new BigDecimal("600"), ConsultationMode.IN_PERSON, DayOfWeek.TUESDAY,   "DERMATOLOGY"),
            new DoctorSeed("Arjun",   "Mehta",  "arjun.mehta@bookmydoct.com",   "9000000003",
                "DCI100003", "NeuroLife Hospital",         "Connaught Place","Delhi",      "Delhi",       "110001", 12, new BigDecimal("900"), ConsultationMode.IN_PERSON, DayOfWeek.WEDNESDAY, "NEUROLOGY"),
            new DoctorSeed("Sneha",   "Reddy",  "sneha.reddy@bookmydoct.com",   "9000000004",
                "NMC100004", "OrthoPlus Clinic",           "Koramangala",    "Bangalore",  "Karnataka",   "560034",  8, new BigDecimal("700"), ConsultationMode.IN_PERSON, DayOfWeek.THURSDAY,  "ORTHOPEDICS"),
            new DoctorSeed("Vikram",  "Singh",  "vikram.singh@bookmydoct.com",  "9000000005",
                "PMC100005", "ChildCare Centre",           "Banjara Hills",  "Hyderabad",  "Telangana",   "500034", 14, new BigDecimal("500"), ConsultationMode.ONLINE,    DayOfWeek.FRIDAY,    "PEDIATRICS"),
            new DoctorSeed("Ananya",  "Gupta",  "ananya.gupta@bookmydoct.com",  "9000000006",
                "CMC100006", "MindWell Clinic",            "FC Road",        "Pune",       "Maharashtra", "411004",  9, new BigDecimal("750"), ConsultationMode.ONLINE,    DayOfWeek.MONDAY,    "PSYCHIATRY"),
            new DoctorSeed("Karthik", "Nair",   "karthik.nair@bookmydoct.com",  "9000000007",
                "KSM100007", "Clear Vision Eye Hospital",  "Marine Drive",   "Kochi",      "Kerala",      "682031", 11, new BigDecimal("650"), ConsultationMode.IN_PERSON, DayOfWeek.TUESDAY,   "OPHTHALMOLOGY"),
            new DoctorSeed("Divya",   "Menon",  "divya.menon@bookmydoct.com",   "9000000008",
                "KER100008", "Women's Health Clinic",      "Avinashi Road",  "Coimbatore", "Tamil Nadu",  "641018",  7, new BigDecimal("600"), ConsultationMode.IN_PERSON, DayOfWeek.WEDNESDAY, "GYNECOLOGY"),
            new DoctorSeed("Suresh",  "Patel",  "suresh.patel@bookmydoct.com",  "9000000009",
                "GUJ100009", "ENT Care Hospital",          "CG Road",        "Ahmedabad",  "Gujarat",     "380009", 13, new BigDecimal("550"), ConsultationMode.IN_PERSON, DayOfWeek.THURSDAY,  "ENT"),
            new DoctorSeed("Meera",   "Joshi",  "meera.joshi@bookmydoct.com",   "9000000010",
                "RAJ100010", "City Medical Centre",        "MI Road",        "Jaipur",     "Rajasthan",   "302001",  6, new BigDecimal("400"), ConsultationMode.BOTH,      DayOfWeek.FRIDAY,    "GENERAL_MEDICINE")
        );

        int created = 0;
        for (DoctorSeed seed : seeds) {
            try {
                // Each doctor runs in its own @Transactional via DoctorSeederHelper proxy
                if (doctorSeederHelper.seedOne(seed)) {
                    log.info("[DataSeeder] Seeded Dr. {} {} ({})", seed.firstName(), seed.lastName(), seed.specCode());
                    created++;
                }
            } catch (Exception e) {
                log.error("[DataSeeder] Failed to seed Dr. {} {}: {}", seed.firstName(), seed.lastName(), e.getMessage());
            }
        }

        if (created > 0) {
            log.info("[DataSeeder] Done. {} doctor(s) seeded with 09:00–17:00 schedules and 90 days of slots.", created);
        } else {
            log.info("[DataSeeder] All doctors already seeded — skipped.");
        }
    }
}
