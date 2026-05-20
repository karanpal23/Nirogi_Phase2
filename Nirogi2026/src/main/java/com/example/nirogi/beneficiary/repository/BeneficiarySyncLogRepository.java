package com.example.nirogi.beneficiary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nirogi.beneficiary.entity.BeneficiarySyncLog;

public interface BeneficiarySyncLogRepository
extends JpaRepository<BeneficiarySyncLog, Long> {
}