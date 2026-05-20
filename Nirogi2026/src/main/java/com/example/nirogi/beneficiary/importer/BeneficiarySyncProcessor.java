package com.example.nirogi.beneficiary.importer;

import com.example.nirogi.beneficiary.dto.BeneficiaryPayloadDTO;
import com.example.nirogi.beneficiary.entity.*;
import com.example.nirogi.beneficiary.enums.*;
import com.example.nirogi.beneficiary.mapper.BeneficiaryMapper;
import com.example.nirogi.beneficiary.repository.*;
import com.example.nirogi.beneficiary.rules.BeneficiaryComparisonRule;
import com.example.nirogi.beneficiary.util.BeneficiaryHashUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BeneficiarySyncProcessor {

    private final BeneficiaryRepository beneficiaryRepo;

    private final BeneficiarySnapshotRepository snapshotRepo;

    private final BeneficiarySyncLogRepository logRepo;

    private final BeneficiaryMapper beneficiaryMapper;

    private final BeneficiaryComparisonRule comparisonRule;

    private final BeneficiaryHashUtil hashUtil;


    public void process(
            BeneficiaryPayloadDTO dto,
            String batchId
    ) {

        OperationType operationType =
                OperationType.valueOf(
                        batchId.split("_")[0]
                );

        switch (operationType) {

            case NEW:
                processNew(dto, batchId);
                break;

            case MOD:
                processMod(dto, batchId);
                break;

            case DEL:
                processDelete(dto, batchId);
                break;
        }
    }


    private void processNew(
            BeneficiaryPayloadDTO dto,
            String batchId
    ) {

        Optional<Beneficiary> existing =
                beneficiaryRepo.findByMemberId(
                        dto.getMemberId()
                );

        if (existing.isPresent()) {

            createLog(
                    dto,
                    batchId,
                    OperationType.NEW,
                    SyncStatus.SKIPPED,
                    "Beneficiary already exists. Use MOD batch."
            );

            return;
        }

        Beneficiary beneficiary =
                beneficiaryMapper.mapToEntity(dto);

        beneficiaryRepo.save(beneficiary);

        String hash =
                hashUtil.generateHash(dto);

        createSnapshot(
                beneficiary,
                batchId,
                hash,
                1,
                true,
                BeneficiaryStatus.ACTIVE
        );

        createLog(
                dto,
                batchId,
                OperationType.NEW,
                SyncStatus.SUCCESS,
                "Beneficiary created"
        );
    }


    private void processMod(
            BeneficiaryPayloadDTO dto,
            String batchId
    ) {

        Beneficiary beneficiary =
                beneficiaryRepo.findByMemberId(
                        dto.getMemberId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Beneficiary not found"
                        )
                );

        beneficiary.setPppId(
                dto.getPppId()
        );

        beneficiary.setFirstName(
                dto.getFirstName()
        );

        beneficiary.setLastName(
                dto.getLastName()
        );

        beneficiary.setFatherHusbandFirstname(
                dto.getFatherHusbandFirstname()
        );

        beneficiary.setFatherHusbandLastname(
                dto.getFatherHusbandLastname()
        );

        beneficiary.setBlockTownCity(
                dto.getBlockTownCity()
        );

        beneficiary.setWardVillage(
                dto.getWardVillage()
        );

        beneficiary.setDistrict(
                dto.getDistrict()
        );

        beneficiary.setDistrictLgd(
                dto.getDistrictLgd()
        );

        beneficiary.setBlockLgd(
                dto.getBlockLgd()
        );

        beneficiary.setWardLgd(
                dto.getWardLgd()
        );

        beneficiary.setIncome(
                dto.getIncome()
        );

        beneficiary.setAge(
                dto.getAge()
        );

        if (dto.getDateOfBirth() != null
                && !dto.getDateOfBirth().trim().isEmpty()) {

            beneficiary.setDateOfBirth(
                    LocalDate.parse(
                            dto.getDateOfBirth()
                    )
            );
        }

        beneficiary.setGender(
                dto.getGender()
        );

        beneficiary.setMobileNo(
                dto.getMobileNo()
        );

        beneficiary.setAddress(
                dto.getAddress()
        );

        beneficiary.setUpdatedAt(
                LocalDateTime.now()
        );

        beneficiaryRepo.save(beneficiary);

        BeneficiarySnapshot latestSnapshot =
                snapshotRepo
                .findByBeneficiaryIdAndIsLatestTrue(
                        beneficiary.getId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Latest snapshot missing"
                        )
                );

        String newHash =
                hashUtil.generateHash(dto);

        boolean requiresVersion =
                comparisonRule.requiresNewVersion(
                        latestSnapshot,
                        newHash
                );

        if (!requiresVersion) {

            createLog(
                    dto,
                    batchId,
                    OperationType.MOD,
                    SyncStatus.SKIPPED,
                    "No changes detected"
            );

            return;
        }

        latestSnapshot.setIsLatest(false);

        snapshotRepo.save(latestSnapshot);

        createSnapshot(
                beneficiary,
                batchId,
                newHash,
                latestSnapshot.getVersionNo() + 1,
                true,
                BeneficiaryStatus.ACTIVE
        );

        createLog(
                dto,
                batchId,
                OperationType.MOD,
                SyncStatus.SUCCESS,
                "Beneficiary modified"
        );
    }


    private void processDelete(
            BeneficiaryPayloadDTO dto,
            String batchId
    ) {

        Beneficiary beneficiary =
                beneficiaryRepo.findByMemberId(
                        dto.getMemberId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Beneficiary not found"
                        )
                );

        BeneficiarySnapshot latestSnapshot =
                snapshotRepo
                .findByBeneficiaryIdAndIsLatestTrue(
                        beneficiary.getId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Latest snapshot missing"
                        )
                );

        latestSnapshot.setIsLatest(false);

        snapshotRepo.save(latestSnapshot);

        createSnapshot(
                beneficiary,
                batchId,
                latestSnapshot.getRecordHash(),
                latestSnapshot.getVersionNo() + 1,
                true,
                BeneficiaryStatus.REMOVED
        );

        createLog(
                dto,
                batchId,
                OperationType.DEL,
                SyncStatus.SUCCESS,
                "Beneficiary removed"
        );
    }


    private void createSnapshot(
            Beneficiary beneficiary,
            String batchId,
            String hash,
            Integer version,
            Boolean latest,
            BeneficiaryStatus status
    ) {

        BeneficiarySnapshot snapshot =
                BeneficiarySnapshot.builder()

                        .beneficiaryId(
                                beneficiary.getId()
                        )

                        .pppId(
                                beneficiary.getPppId()
                        )

                        .income(
                                beneficiary.getIncome()
                        )

                        .recordHash(
                                hash
                        )

                        .versionNo(
                                version
                        )

                        .isLatest(
                                latest
                        )

                        .beneficiaryStatus(
                                status
                        )

                        .batchId(
                                batchId
                        )

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .build();

        snapshotRepo.save(snapshot);
    }


    private void createLog(
            BeneficiaryPayloadDTO dto,
            String batchId,
            OperationType operationType,
            SyncStatus syncStatus,
            String remarks
    ) {

        BeneficiarySyncLog log =
                BeneficiarySyncLog.builder()

                        .memberId(
                                dto.getMemberId()
                        )

                        .batchId(
                                batchId
                        )

                        .operationType(
                                operationType
                        )

                        .status(
                                syncStatus
                        )

                        .message(
                                remarks
                        )

                        .processedAt(
                                LocalDateTime.now()
                        )

                        .build();

        logRepo.save(log);
    }
}