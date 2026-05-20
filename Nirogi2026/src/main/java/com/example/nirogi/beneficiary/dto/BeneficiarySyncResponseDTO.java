package com.example.nirogi.beneficiary.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiarySyncResponseDTO {

    private int success;

    private int failed;

    private Long skipped;
}
