package com.example.nirogi.beneficiary.serviceImpl;


import com.example.nirogi.beneficiary.dto.*;
import com.example.nirogi.beneficiary.entity.UploadBatch;
import com.example.nirogi.beneficiary.enums.BatchStatus;
import com.example.nirogi.beneficiary.enums.OperationType;
import com.example.nirogi.beneficiary.importer.BeneficiarySyncProcessor;
import com.example.nirogi.beneficiary.repository.UploadBatchRepository;
import com.example.nirogi.beneficiary.service.BeneficiaryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BeneficiaryServiceImpl
        implements BeneficiaryService {

    private final BeneficiarySyncProcessor processor;

    private final UploadBatchRepository batchRepo;


    @Override
    public BeneficiarySyncResponseDTO syncBeneficiaries(
            BeneficiarySyncRequestDTO request,
            Long adminId
    ) {

        OperationType operationType =
                OperationType.valueOf(
                        request.getBatchId()
                                .split("_")[0]
                );

        UploadBatch batch =
                UploadBatch.builder()

                        .batchId(
                                request.getBatchId()
                        )

                        .operationType(
                                operationType
                        )

                        .uploadedBy(
                                adminId
                        )

                        .uploadedAt(
                                LocalDateTime.now()
                        )

                        .totalRecords(
                                request.getBeneficiaries().size()
                        )

                        .status(
                                BatchStatus.PROCESSING
                        )

                        .build();

        batchRepo.save(batch);

        int success = 0;
        int failed = 0;

        for (BeneficiaryPayloadDTO dto
                : request.getBeneficiaries()) {

            try {

                processor.process(
                        dto,
                        request.getBatchId()
                );

                success++;

            } catch (Exception ex) {

                failed++;
            }
        }

        /*
            FINAL STATUS
         */

        if (failed > 0) {

            batch.setStatus(
                    BatchStatus.FAILED
            );

        } else {

            batch.setStatus(
                    BatchStatus.COMPLETED
            );
        }

        batchRepo.save(batch);

        return BeneficiarySyncResponseDTO
                .builder()
                .success(success)
                .failed(failed)
                .build();
    }
}