package com.example.nirogi.screening.repository;


import com.example.nirogi.screening.entity.ScreeningRecord;
import com.example.nirogi.screening.enums.ScreeningStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ScreeningRecordRepository
        extends JpaRepository<ScreeningRecord, Long> {

    Optional<ScreeningRecord>
    findByReferenceId(String referenceId);

    boolean existsByBeneficiaryIdAndStatusAndScreeningDateAfter(
            Long beneficiaryId,
            ScreeningStatus status,
            LocalDateTime date
    );
    
    Optional<ScreeningRecord>
    findTopByBeneficiaryIdOrderByCreatedAtDesc(
            Long beneficiaryId
    );
}
