package com.example.nirogi.eligibility.serviceImpl;



import com.example.nirogi.beneficiary.entity.Beneficiary;
import com.example.nirogi.beneficiary.entity.BeneficiarySnapshot;
import com.example.nirogi.beneficiary.repository.BeneficiaryRepository;
import com.example.nirogi.beneficiary.repository.BeneficiarySnapshotRepository;

import com.example.nirogi.eligibility.dto.*;
import com.example.nirogi.eligibility.entity.EligibilityScheme;
import com.example.nirogi.eligibility.enums.*;
import com.example.nirogi.eligibility.repository.EligibilitySchemeRepository;
import com.example.nirogi.eligibility.rules.EligibilityRuleEngine;
import com.example.nirogi.eligibility.service.EligibilityService;
import com.example.nirogi.eligibility.validator.EligibilitySchemeValidator;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EligibilityServiceImpl
        implements EligibilityService {

    private final EligibilitySchemeRepository schemeRepo;

    private final BeneficiaryRepository beneficiaryRepo;

    private final BeneficiarySnapshotRepository snapshotRepo;

    private final EligibilityRuleEngine ruleEngine;

    private final EligibilitySchemeValidator validator;


    @Override
    public EligibilitySchemeResponseDTO createScheme(
            CreateEligibilitySchemeDTO request,
            Long adminId
    ) {

        validator.validate(request);

        EligibilityScheme scheme =
                EligibilityScheme.builder()
                        .schemeCode(
                                SchemeCode.valueOf(
                                        request.getSchemeCode()
                                )
                        )
                        .schemeName(
                                request.getSchemeName()
                        )
                        .minAge(
                                request.getMinAge()
                        )
                        .maxAge(
                                request.getMaxAge()
                        )
                        .incomeLimit(
                                request.getIncomeLimit()
                        )
                        .incomeBased(
                                request.getIncomeBased()
                        )
                        .status(
                                SchemeStatus.ACTIVE
                        )
                        .createdBy(adminId)
                        .createdAt(LocalDateTime.now())
                        .build();

        schemeRepo.save(scheme);

        return mapResponse(scheme);
    }


    @Override
    public List<EligibilitySchemeResponseDTO>
    getActiveSchemes() {

        return schemeRepo.findByStatus(
                SchemeStatus.ACTIVE
        ).stream()
                .map(this::mapResponse)
                .collect(Collectors.toList());
    }


    @Override
    public EligibilityResponseDTO checkEligibility(
            String memberId
    ) {

        Beneficiary beneficiary =
                beneficiaryRepo.findByMemberId(memberId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Beneficiary not found"
                                )
                        );

        BeneficiarySnapshot snapshot =
                snapshotRepo
                        .findByBeneficiaryIdAndIsLatestTrue(
                                beneficiary.getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "No latest snapshot found"
                                )
                        );

        List<EligibilityScheme> schemes =
                schemeRepo.findByStatus(
                        SchemeStatus.ACTIVE
                );

        if (schemes.isEmpty()) {

            throw new RuntimeException(
                    "No active schemes found"
            );
        }

        return ruleEngine.evaluate(
                beneficiary,
                snapshot,
                schemes
        );
    }


    private EligibilitySchemeResponseDTO mapResponse(
            EligibilityScheme scheme
    ) {

        return EligibilitySchemeResponseDTO
                .builder()
                .id(scheme.getId())
                .schemeCode(
                        scheme.getSchemeCode().name()
                )
                .schemeName(
                        scheme.getSchemeName()
                )
                .minAge(
                        scheme.getMinAge()
                )
                .maxAge(
                        scheme.getMaxAge()
                )
                .incomeLimit(
                        scheme.getIncomeLimit()
                )
                .incomeBased(
                        scheme.getIncomeBased()
                )
                .status(
                        scheme.getStatus().name()
                )
                .build();
    }
}