package com.example.nirogi.screening.dto;


import lombok.Data;

@Data
public class CreateScreeningDTO {

    private String memberId;

    private String category;

    private Long districtId;

    private Long facilityId;

    private Boolean labRequired;
}
