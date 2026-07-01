package com.bookmydoct.doctor.service.impl;


import com.bookmydoct.common.exception.customException.SpecializationAlreadyExistsException;
import com.bookmydoct.common.exception.customException.SpecializationNotFoundException;
import com.bookmydoct.doctor.data.dto.request.SpecializationRequest;
import com.bookmydoct.doctor.data.dto.response.SpecializationResponse;
import com.bookmydoct.doctor.data.entity.Specialization;
import com.bookmydoct.doctor.repository.SpecializationRepository;
import com.bookmydoct.doctor.service.SpecializationService;
import com.bookmydoct.doctor.util.SpecializationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;

    @Override
    public SpecializationResponse addSpecialization(SpecializationRequest request) {

        if (specializationRepository.existsByCode(request.getCode())) {
            throw new SpecializationAlreadyExistsException("Specialization code already exists");
        }

        if (specializationRepository.existsByName(request.getName())) {
            throw new SpecializationAlreadyExistsException("Specialization name already exists");
        }

        Specialization specialization = Specialization.builder()
                                                    .code(request.getCode())
                                                    .name(request.getName())
                                                    .description(request.getDescription())
                                                    .active(request.getActive())
                                                    .build();

        Specialization saved = specializationRepository.save(specialization);

        return SpecializationMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SpecializationResponse getSpecializationByUuid(UUID specializationUuid) {

        Specialization specialization = specializationRepository.findByUuid(specializationUuid)
                                              .orElseThrow(() -> new SpecializationNotFoundException("Specialization not found"));

        return SpecializationMapper.toResponse(specialization);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecializationResponse> getAllSpecializations() {

        return specializationRepository
                .findAll()
                .stream()
                .map(SpecializationMapper::toResponse)
                .toList();
    }

}
