package com.example.nirogi.beneficiary.service;


import com.example.nirogi.beneficiary.dto.BeneficiarySyncRequestDTO;
import com.example.nirogi.beneficiary.dto.BeneficiarySyncResponseDTO;


public interface BeneficiaryService {

    BeneficiarySyncResponseDTO syncBeneficiaries(
            BeneficiarySyncRequestDTO request,
            Long userId
    );
}
