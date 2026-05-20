package com.example.nirogi.screening.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabTestViewDTO {

    private String testCode;

    private String testName;

    private String status;

    private String resultValue;

    private String remarks;

    private String normalRange;
}
