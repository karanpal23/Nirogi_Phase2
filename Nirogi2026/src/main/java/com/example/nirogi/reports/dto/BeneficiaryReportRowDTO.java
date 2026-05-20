package com.example.nirogi.reports.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiaryReportRowDTO {

    private Long serialNo;

    private String district;

    private String facilityName;

    private String designation;

    private String referenceId;

    private String pppId;

    private String beneficiaryName;

    private String ageGender;

    private String address;

    private String diagnosisAlreadyKnown;

    private String diagnosed;

    private String sampleSent;

    private String reportEntered;

    private String category;
}