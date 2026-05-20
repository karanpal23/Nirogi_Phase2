package com.example.nirogi.screening.service;

import com.example.nirogi.screening.dto.CreateLabOrderDTO;
import com.example.nirogi.screening.dto.LabOrderResponseDTO;
import com.example.nirogi.screening.dto.LabOrderViewDTO;
import com.example.nirogi.screening.dto.SubmitLabResultsDTO;

public interface LabWorkflowService {
	
	LabOrderResponseDTO createLabOrder(
            CreateLabOrderDTO request,
            Long userId
    );
	
	LabOrderViewDTO getLabTests(
	        String referenceId
	);

	LabOrderResponseDTO submitLabResults(
	        SubmitLabResultsDTO request,
	        Long userId
	);

}
