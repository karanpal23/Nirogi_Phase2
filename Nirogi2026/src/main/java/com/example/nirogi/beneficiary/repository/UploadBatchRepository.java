package com.example.nirogi.beneficiary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nirogi.beneficiary.entity.UploadBatch;

public interface UploadBatchRepository
extends JpaRepository<UploadBatch, Long> {
}