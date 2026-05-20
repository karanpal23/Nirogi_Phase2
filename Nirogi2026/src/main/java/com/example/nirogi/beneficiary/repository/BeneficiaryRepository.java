package com.example.nirogi.beneficiary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nirogi.beneficiary.entity.Beneficiary;

public interface BeneficiaryRepository
extends JpaRepository<Beneficiary, Long> {

Optional<Beneficiary>
findByMemberId(String memberId);
}
