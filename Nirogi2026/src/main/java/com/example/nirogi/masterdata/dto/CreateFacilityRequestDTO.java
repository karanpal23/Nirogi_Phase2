package com.example.nirogi.masterdata.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateFacilityRequestDTO {

    @NotBlank
    private String name;

    private Long districtId;

    private Long facilityTypeId;
}