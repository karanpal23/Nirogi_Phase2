package com.example.nirogi.eligibility.dto;



import lombok.Data;

@Data
public class CreateEligibilitySchemeDTO {

    private String schemeCode;

    private String schemeName;

    private Integer minAge;

    private Integer maxAge;

    private Double incomeLimit;

    private Boolean incomeBased;
}