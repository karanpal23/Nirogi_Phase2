package com.example.nirogi.screening.serviceImpl;



import com.example.nirogi.beneficiary.entity.Beneficiary;
import com.example.nirogi.screening.dto.*;
import com.example.nirogi.screening.entity.*;
import com.example.nirogi.screening.enums.*;
import com.example.nirogi.screening.repository.*;
import com.example.nirogi.screening.service.ScreeningService;
import com.example.nirogi.screening.util.ReferenceIdGenerator;
import com.example.nirogi.screening.validator.ScreeningEligibilityValidator;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl
        implements ScreeningService {

    private final ScreeningRecordRepository screeningRepo;

    private final LabWorkflowRepository labRepo;

    private final ScreeningEligibilityValidator validator;

    private final ReferenceIdGenerator referenceIdGenerator;


    @Override
    public ScreeningResponseDTO createScreening(
            CreateScreeningDTO request,
            Long doctorId
    ) {

        /*
            VALIDATE BENEFICIARY
         */

        ScreeningValidationResultDTO validation =
                validator.validate(
                        request.getMemberId()
                );

        Beneficiary beneficiary =
                validation.getBeneficiary();

        /*
            CHECK EXISTING SCREENING
         */

        Optional<ScreeningRecord> latestScreening =
                screeningRepo
                        .findTopByBeneficiaryIdOrderByCreatedAtDesc(
                                beneficiary.getId()
                        );

        if (latestScreening.isPresent()) {

            ScreeningRecord existing =
                    latestScreening.get();

            LocalDate lastScreeningDate =
                    existing.getCreatedAt()
                            .toLocalDate();

            LocalDate today =
                    LocalDate.now();

            long years =
                    ChronoUnit.YEARS.between(
                            lastScreeningDate,
                            today
                    );

            /*
                WITHIN 2 YEARS
             */

            if (years < 2) {

                return ScreeningResponseDTO
                        .builder()

                        .referenceId(
                                existing.getReferenceId()
                        )

                        .status(
                                existing.getStatus().name()
                        )

                        .eligibleScheme(
                                existing.getEligibleScheme()
                        )

                        .message(
                                "Existing screening already active"
                        )

                        .build();
            }
        }

        /*
            CREATE NEW SCREENING
         */

        String referenceId =
                referenceIdGenerator.generate();

        ScreeningStatus status =
                request.getLabRequired()
                        ?
                        ScreeningStatus.PENDING_LAB
                        :
                        ScreeningStatus.COMPLETED;

        ScreeningRecord screening =
                ScreeningRecord.builder()

                        .referenceId(
                                referenceId
                        )

                        .beneficiaryId(
                                beneficiary.getId()
                        )

                        .screeningCategory(
                                ScreeningCategory.valueOf(
                                        request.getCategory()
                                )
                        )

                        .eligibleScheme(
                                validation.getEligibleScheme()
                        )

                        .districtId(
                                request.getDistrictId()
                        )

                        .facilityId(
                                request.getFacilityId()
                        )

                        .doctorId(
                                doctorId
                        )

                        .labRequired(
                                request.getLabRequired()
                        )

                        .screeningDate(
                                LocalDateTime.now()
                        )

                        .labDueDate(
                                request.getLabRequired()
                                ?
                                LocalDateTime.now()
                                        .plusDays(7)
                                :
                                null
                        )

                        .status(
                                status
                        )

                        .completedAt(
                                request.getLabRequired()
                                ?
                                null
                                :
                                LocalDateTime.now()
                        )

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .build();

        screeningRepo.save(screening);

        /*
            CREATE LAB WORKFLOW
         */

        if (request.getLabRequired()) {

            LabWorkflow workflow =
                    LabWorkflow.builder()

                            .screeningRecordId(
                                    screening.getId()
                            )

                            .labStatus(
                                    LabStatus.SENT
                            )

                            .sentAt(
                                    LocalDateTime.now()
                            )

                            .dueDate(
                                    LocalDateTime.now()
                                            .plusDays(7)
                            )

                            .enteredBy(
                                    doctorId
                            )

                            .build();

            labRepo.save(workflow);
        }

        return ScreeningResponseDTO
                .builder()

                .referenceId(
                        referenceId
                )

                .status(
                        status.name()
                )

                .eligibleScheme(
                        validation.getEligibleScheme()
                )

                .message(
                        "Screening created successfully"
                )

                .build();
    }


    @Override
    public ScreeningResponseDTO submitLabResult(
            LabResultDTO request,
            Long userId
    ) {

        ScreeningRecord screening =
                screeningRepo.findByReferenceId(
                        request.getReferenceId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Invalid reference ID"
                        )
                );

        screening.setStatus(
                ScreeningStatus.COMPLETED
        );

        screening.setCompletedAt(
                LocalDateTime.now()
        );

        screeningRepo.save(screening);

        return ScreeningResponseDTO
                .builder()

                .referenceId(
                        screening.getReferenceId()
                )

                .status(
                        ScreeningStatus.COMPLETED.name()
                )

                .message(
                        "Lab result submitted successfully"
                )

                .build();
    }
}