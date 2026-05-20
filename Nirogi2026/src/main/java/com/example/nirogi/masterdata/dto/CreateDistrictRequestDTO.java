package com.example.nirogi.masterdata.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateDistrictRequestDTO {

    @NotBlank
    private String name;
}