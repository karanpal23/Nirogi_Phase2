package com.example.nirogi.masterdata.dto;


import lombok.Data;

@Data
public class CreateMasterRequestDTO {

    private String name;

    // optional fields (used depending on entity)
    private String code;

    private Long districtId;

    private Long facilityTypeId;

    private String description;
}