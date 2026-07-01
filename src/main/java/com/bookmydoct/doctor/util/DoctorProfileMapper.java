package com.bookmydoct.doctor.util;

import com.bookmydoct.doctor.data.dto.response.DoctorSearchResponse;
import com.bookmydoct.doctor.data.entity.DoctorProfile;
import com.bookmydoct.doctor.data.dto.response.DoctorProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class DoctorProfileMapper {

    public DoctorProfileResponse toResponse(DoctorProfile doctorProfile) {

        return DoctorProfileResponse.builder()
                .doctorUuid(doctorProfile.getUuid())
                .userUuid(doctorProfile.getUser().getUuid())
                .doctorName(doctorProfile.getUser().getFirstName() + " " + doctorProfile.getUser().getLastName())
                .specializationUuid(doctorProfile.getSpecialization().getUuid())
                .specializationCode(doctorProfile.getSpecialization().getCode())
                .specializationName(doctorProfile.getSpecialization().getName())
                .experienceYears(doctorProfile.getExperienceYears())
                .consultationFee(doctorProfile.getConsultationFee())
                .averageRating(doctorProfile.getAverageRating())
                .totalReviews(doctorProfile.getTotalReviews())
                .licenseNumber(doctorProfile.getLicenseNumber())
                .clinicName(doctorProfile.getClinicName())
                .address(doctorProfile.getAddress())
                .city(doctorProfile.getCity())
                .state(doctorProfile.getState())
                .pincode(doctorProfile.getPincode())
                .consultationMode(doctorProfile.getConsultationMode())
                .verificationStatus(doctorProfile.getVerificationStatus())
                .build();
    }

    public static DoctorSearchResponse toSearchResponse(DoctorProfile profile) {

        return DoctorSearchResponse.builder()
                .uuid(profile.getUuid())
                .firstName(profile.getUser().getFirstName())
                .lastName(profile.getUser().getLastName())
                .specializationName(profile.getSpecialization().getName())
                .experienceYears(profile.getExperienceYears())
                .consultationFee(profile.getConsultationFee())
                .averageRating(profile.getAverageRating())
                .totalReviews(profile.getTotalReviews())
                .clinicName(profile.getClinicName())
                .city(profile.getCity())
                .consultationMode(
                        profile.getConsultationMode() != null
                                ? profile.getConsultationMode().name() : null)
                .verificationStatus(
                        profile.getVerificationStatus() != null
                                ? profile.getVerificationStatus().name() : null)
                .build();
    }

}