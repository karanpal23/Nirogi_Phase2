package com.example.nirogi.masterdata.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateFacilityTypeRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String code;
}
