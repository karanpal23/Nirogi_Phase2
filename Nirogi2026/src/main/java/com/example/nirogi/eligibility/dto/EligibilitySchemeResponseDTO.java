package com.example.nirogi.eligibility.dto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EligibilitySchemeResponseDTO {

    private Long id;

    private String schemeCode;

    private String schemeName;

    private Integer minAge;

    private Integer maxAge;

    private Double incomeLimit;

    private Boolean incomeBased;

    private String status;
}