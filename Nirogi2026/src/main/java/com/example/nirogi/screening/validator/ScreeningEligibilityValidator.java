package com.example.nirogi.screening.validator;


import com.example.nirogi.beneficiary.entity.Beneficiary;
import com.example.nirogi.beneficiary.repository.BeneficiaryRepository;

import com.example.nirogi.eligibility.dto.EligibilityResponseDTO;
import com.example.nirogi.eligibility.service.EligibilityService;

import com.example.nirogi.screening.dto.ScreeningValidationResultDTO;
import com.example.nirogi.screening.enums.ScreeningStatus;
import com.example.nirogi.screening.repository.ScreeningRecordRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScreeningEligibilityValidator {

    private final BeneficiaryRepository beneficiaryRepo;

    private final EligibilityService eligibilityService;

    private final ScreeningRecordRepository screeningRepo;


    public ScreeningValidationResultDTO validate(
            String memberId
    ) {

        Beneficiary beneficiary =
                beneficiaryRepo.findByMemberId(memberId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Beneficiary not found"
                                )
                        );

        EligibilityResponseDTO eligibility =
                eligibilityService.checkEligibility(
                        memberId
                );

        if (!eligibility.getEligible()) {

            throw new RuntimeException(
                    eligibility.getReason()
            );
        }

        boolean alreadyScreened =
                screeningRepo
                .existsByBeneficiaryIdAndStatusAndScreeningDateAfter(
                        beneficiary.getId(),
                        ScreeningStatus.COMPLETED,
                        LocalDateTime.now().minusYears(2)
                );

        if (alreadyScreened) {

            throw new RuntimeException(
                    "Beneficiary already screened within 2 years"
            );
        }

        return ScreeningValidationResultDTO
                .builder()
                .beneficiary(beneficiary)
                .eligibleScheme(
                        eligibility.getEligibleScheme()
                )
                .build();
    }
}