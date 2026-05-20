package com.example.nirogi.screening.dto;

import com.example.nirogi.beneficiary.entity.Beneficiary;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningValidationResultDTO {

    private Beneficiary beneficiary;

    private String eligibleScheme;
}
