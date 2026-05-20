package com.example.nirogi.screening.repository;

import com.example.nirogi.screening.entity.LabWorkflow;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LabWorkflowRepository
        extends JpaRepository<LabWorkflow, Long> {
	
	Optional<LabWorkflow>
	findByScreeningRecordId(
	        Long screeningRecordId
	);
}
