package com.example.nirogi.masterdata.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateDesignationRequestDTO {

    @NotBlank
    private String name;

    private String description;
}
