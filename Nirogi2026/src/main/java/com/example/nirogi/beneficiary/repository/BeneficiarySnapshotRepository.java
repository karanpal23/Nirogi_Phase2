package com.example.nirogi.beneficiary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nirogi.beneficiary.entity.BeneficiarySnapshot;

public interface BeneficiarySnapshotRepository
extends JpaRepository<BeneficiarySnapshot, Long> {

Optional<BeneficiarySnapshot>
findByBeneficiaryIdAndIsLatestTrue(Long beneficiaryId);
}