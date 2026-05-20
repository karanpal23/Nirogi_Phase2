package com.example.nirogi.beneficiary.dto;

import java.util.List;

import lombok.Data;

@Data
public class BeneficiarySyncRequestDTO {

    private String batchId;

    private List<BeneficiaryPayloadDTO> beneficiaries;
}
